package com.fortressofnerditude.createbroadcast.item;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateBroadcast.MODID);

    public static final Supplier<CreativeModeTab> CREATE_BROADCAST_TAB = CREATIVE_MODE_TAB.register("create_broadcast_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BRASSANTENNA.get()))
                    .title(Component.translatable("creativetab.create_broadcast.create_broadcast_tab"))
                    .displayItems((ItemDisplayParameters, output) -> {
                        output.accept(ModItems.BRASSANTENNA);
                        output.accept(ModItems.WALKIE);
                        output.accept(ModItems.BRASS_WALKIE);
                        output.accept(ModItems.ENDER_WALKIE);
                        output.accept(ModBlocks.TRANSMITTER);
                        output.accept(ModItems.ENDERANTENNA);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
