package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import de.maxhenkel.voicechat.api.audiochannel.AudioChannel;
import de.maxhenkel.voicechat.api.audiochannel.EntityAudioChannel;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
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
}
