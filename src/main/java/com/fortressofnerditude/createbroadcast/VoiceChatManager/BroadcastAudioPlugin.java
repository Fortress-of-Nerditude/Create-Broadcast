package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import net.neoforged.bus.api.SubscribeEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Enumeration;

@ForgeVoicechatPlugin
public class BroadcastAudioPlugin implements VoicechatPlugin {
    public static String BROADCAST_CATEGORY = "broadcast";

    public static VoicechatServerApi api;

    @Override
    public void initialize(VoicechatApi api) {
        CreateBroadcast.LOGGER.info("voice chat plugin found");
    }


    @Override
    public String getPluginId() {
        return "create_broadcast";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
        registration.registerEvent(MicrophonePacketEvent.class, FrequencyManager::onPlayerTalking);
    }

    @SubscribeEvent
    private void onServerStarted(VoicechatServerStartedEvent event) {
        api = event.getVoicechat();
        VolumeCategory broadcast = api.volumeCategoryBuilder()
                .setId(BROADCAST_CATEGORY)
                .setName("Broadcasts")
                .setDescription("The volume of all radio broadcasts & walkie talkies")
                .setIcon(getIcon("broadcast_icon.png"))
                .build();

        api.registerVolumeCategory(broadcast);
        CreateBroadcast.LOGGER.info("api: " + api);
    }

    @Nullable
    private int[][] getIcon(String path) {
        try {
            Enumeration<URL> resources = Plugin.class.getClassLoader().getResources(path);
            while (resources.hasMoreElements()) {
                BufferedImage bufferedImage = ImageIO.read(resources.nextElement().openStream());
                if (bufferedImage.getWidth() != 16) {
                    continue;
                }
                if (bufferedImage.getHeight() != 16) {
                    continue;
                }
                int[][] image = new int[16][16];
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        image[x][y] = bufferedImage.getRGB(x, y);
                    }
                }
                return image;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
