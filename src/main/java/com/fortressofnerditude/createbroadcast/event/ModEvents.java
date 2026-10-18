package com.fortressofnerditude.createbroadcast.event;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.fortressofnerditude.createbroadcast.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod("create_broadcast")
public class ModEvents {


    @SubscribeEvent // on the mod event bus only on the physical client
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> { // ItemProperties#register is not threadsafe, so we need to call it on the main thread
            ItemProperties.register(
                    // The item to apply the property to.
                    ModItems.WALKIE.get(),
                    // The id of the property.
                    ResourceLocation.fromNamespaceAndPath("create_broadcast", "active"),
                    // A reference to a method that calculates the override value.
                    // Parameters are the used item stack, the level context, the player using the item,
                    // and a random seed you can use.
                    (stack, level, player, seed) -> checkActive(stack)
            );
        });
    }

    private static float checkActive(ItemStack stack) {
        if (stack.get(ModDataComponents.ACTIVE) == null) {
            stack.set(ModDataComponents.ACTIVE, false);
        }
        if (Boolean.TRUE.equals(stack.get(ModDataComponents.ACTIVE))) {
            return 1.0F;
        } else {
            return 0.0F;
        }
    }
}
