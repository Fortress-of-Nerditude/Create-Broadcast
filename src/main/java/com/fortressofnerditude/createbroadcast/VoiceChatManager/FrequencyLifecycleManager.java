package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.fortressofnerditude.createbroadcast.item.ModItems;
import com.fortressofnerditude.createbroadcast.item.custom.WalkieItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@EventBusSubscriber(modid = "create_broadcast")
public class FrequencyLifecycleManager {

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        FrequencyManager.clear();
    }

    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        for (int i = 0; i < event.getEntity().getInventory().getContainerSize(); i++) {
            ItemStack currentItem = event.getEntity().getInventory().getItem(i);
            if (currentItem.is(ModItems.WALKIE)) {
                currentItem.set(ModDataComponents.ACTIVE, false);
            }
        }
    }

    @SubscribeEvent
    public static void playerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        for (int i = 0; i < event.getEntity().getInventory().getContainerSize(); i++) {
            ItemStack currentItem = event.getEntity().getInventory().getItem(i);
            if (currentItem.is(ModItems.WALKIE) && Boolean.TRUE.equals(currentItem.get(ModDataComponents.ACTIVE))) {
                FrequencyManager.tuneOut(event.getEntity(), currentItem.get(ModDataComponents.FREQUENCY));
            }
        }
    }

}
