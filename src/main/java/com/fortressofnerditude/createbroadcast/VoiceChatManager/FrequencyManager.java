package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import com.fortressofnerditude.createbroadcast.Config;
import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter.TransmitterData;
import com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter.TransmitterInfo;
import com.fortressofnerditude.createbroadcast.block.entity.RadioTransmitterEntity;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.fortressofnerditude.createbroadcast.item.ModItems;
import de.maxhenkel.voicechat.api.audiochannel.AudioChannel;
import de.maxhenkel.voicechat.api.audiochannel.EntityAudioChannel;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class FrequencyManager {
    public static final Map<Integer, Map<Player, AudioChannel>> audioMap = new HashMap<>();

    public static void createFrequency(Integer frequency) {
        audioMap.put(frequency, new HashMap<Player, AudioChannel>());
    }

    public static void tuneIn(Player player, Integer frequency) {
        Map<Player, AudioChannel> frequencyMap = audioMap.get(frequency);
        if (frequencyMap == null) {
            createFrequency(frequency);
            frequencyMap = audioMap.get(frequency);
        }
        if (BroadcastAudioPlugin.api == null) {
            return;
        }
        UUID channel_id = UUID.randomUUID();
        EntityAudioChannel channel = BroadcastAudioPlugin.api.createEntityAudioChannel(
                channel_id,
                BroadcastAudioPlugin.api.fromEntity(player));
        if (channel == null) {
            return;
        }
        channel.setCategory("broadcast");
        channel.setDistance(48);
        frequencyMap.put(player, channel);
    }

    public static void tuneOut(Player player, Integer frequency) {
        Map<Player, AudioChannel> frequencyMap = audioMap.get(frequency);
        if (frequencyMap == null) {
            createFrequency(frequency);
            frequencyMap = audioMap.get(frequency);
        }
        frequencyMap.put(player, null);
    }

    public static void clear() {
        audioMap.clear();
    }

    public static void onPlayerTalking(MicrophonePacketEvent event) {
        if (event.getSenderConnection() == null) {
            return;
        }
        if (event.getSenderConnection().getPlayer().getPlayer() instanceof ServerPlayer sender) {
            for (int i = 0; i < sender.getInventory().getContainerSize(); i++) {
                ItemStack currentItem = sender.getInventory().getItem(i);
                if (currentItem.is(ModItems.WALKIE)) {
                    if (Boolean.TRUE.equals(currentItem.get(ModDataComponents.ACTIVE))) {
                        sendVoice(sender, currentItem.get(ModDataComponents.FREQUENCY), event, Config.WALKIE_RANGE.get());
                    }
                } else if (currentItem.is(ModItems.BRASS_WALKIE)) {
                    if (Boolean.TRUE.equals(currentItem.get(ModDataComponents.ACTIVE))) {
                        sendVoice(sender, currentItem.get(ModDataComponents.FREQUENCY), event, Config.BRASS_WALKIE_RANGE.get());
                    }
                } else if (currentItem.is(ModItems.ENDER_WALKIE)) {
                    if (Boolean.TRUE.equals(currentItem.get(ModDataComponents.ACTIVE))) {
                        sendVoice(sender, currentItem.get(ModDataComponents.FREQUENCY), event, Config.ENDER_WALKIE_RANGE.get());
                    }
                }
            }
            Integer listeningRange = Config.TRANSMITTER_AUDIBLE.get();
            Map<Vec3, TransmitterInfo> transmitters = TransmitterData.readTransmitters();
            for (Map.Entry<Vec3, TransmitterInfo> entry : transmitters.entrySet()) {
                Vec3 transmitterPosition = entry.getKey();
                HashMap<String, Integer> transmitterDump = entry.getValue().dump();
                Integer transmitterFrequency = transmitterDump.get("Frequency");
                Integer transmitterRange = transmitterDump.get("Range");
                Integer rpm = transmitterDump.get("RPM");
                if (Math.abs(sender.position().x - transmitterPosition.x) <= listeningRange &&
                        Math.abs(sender.position().y - transmitterPosition.y) <= listeningRange &&
                        Math.abs(sender.position().z - transmitterPosition.z) <= listeningRange) {
                    int elevation = (int) transmitterPosition.y;
                    Level level = sender.level();
                    while (level.getBlockState(new BlockPos((int) transmitterPosition.x, (elevation + 1), (int) transmitterPosition.z)).is(Blocks.LIGHTNING_ROD)) {
                        elevation += 1;
                    }
                    if (rpm > 0) {
                        sendVoice(sender, transmitterFrequency, event, transmitterRange);
                        CreateBroadcast.LOGGER.info("Range: " + transmitterRange);
                    } else {
                        CreateBroadcast.LOGGER.info("No RPM @ " + transmitterPosition);
                    }
                }
            }
        }
    }

    public static void sendVoice(Player talkingPlayer, Integer frequency, MicrophonePacketEvent event, Integer maxRange) {
        Map<Player, AudioChannel> frequencyMap = audioMap.get(frequency);
        if (frequencyMap != null) {
            byte[] audio = event.getPacket().getOpusEncodedData();
            for (Map.Entry<Player, AudioChannel> entry : frequencyMap.entrySet()) {
                Player player = entry.getKey();
                AudioChannel channel = entry.getValue();
                if (channel != null && (!Objects.equals(talkingPlayer.getUUID().toString(), player.getUUID().toString()))) {
                    Vec3 sendLocation = talkingPlayer.position();
                    Vec3 receiveLocation = player.position();
                    if (Math.abs(sendLocation.x - receiveLocation.x) <= maxRange &&
                            Math.abs(sendLocation.y - receiveLocation.y) <= maxRange &&
                            Math.abs(sendLocation.z - receiveLocation.z) <= maxRange) {
                        channel.send(audio);
                    } else if (maxRange == -1) {
                        channel.send(audio);
                    }
                };
            }
        } else {
            CreateBroadcast.LOGGER.info("Frequency Map Null");
        }
    }
}
