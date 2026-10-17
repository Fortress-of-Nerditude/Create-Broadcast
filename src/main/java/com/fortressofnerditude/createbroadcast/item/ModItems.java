package com.fortressofnerditude.createbroadcast.item;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateBroadcast.MODID);

    public static final DeferredItem<Item> WALKIE = ITEMS.register("walkie_talkie",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ANTENNA = ITEMS.register("antenna",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BRASSANTENNA = ITEMS.register("brass_antenna",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
