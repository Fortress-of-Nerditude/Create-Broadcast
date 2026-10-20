package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import com.fortressofnerditude.createbroadcast.Config;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.fortressofnerditude.createbroadcast.item.ModItems;
import de.maxhenkel.voicechat.api.audiochannel.AudioChannel;
import de.maxhenkel.voicechat.api.audiochannel.EntityAudioChannel;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FrequencyManager {
    public static final Map<Double, Map<Player, AudioChannel>> audioMap = new HashMap<>();

    public static void createFrequency(Double frequency) {
        audioMap.put(frequency, new HashMap<Player, AudioChannel>());
    }

    public static void tuneIn(Player player, Double frequency) {
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
        channel.setCategory("broadcast"); // The category of the audio channel
        channel.setDistance(48);
        frequencyMap.put(player, channel);
    }

    public static void tuneOut(Player player, Double frequency) {
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
                }
            }
        }
    }

    public static void sendVoice(Player talkingPlayer, Double frequency, MicrophonePacketEvent event, Integer maxRange) {
        Map<Player, AudioChannel> frequencyMap = audioMap.get(frequency);
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
                }
            };
        }
    }
}
