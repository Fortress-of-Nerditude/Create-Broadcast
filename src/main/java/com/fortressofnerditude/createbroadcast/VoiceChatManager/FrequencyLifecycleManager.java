package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

@EventBusSubscriber(modid = "create_broadcast")
public class FrequencyLifecycleManager{

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        FrequencyManager.clear();
    }
}
