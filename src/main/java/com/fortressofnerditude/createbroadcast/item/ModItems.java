package com.fortressofnerditude.createbroadcast.item;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.item.custom.WalkieItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateBroadcast.MODID);

    public static final DeferredItem<Item> WALKIE = ITEMS.register("walkie_inactive",
            () -> new WalkieItem(new Item.Properties()));
    public static final DeferredItem<Item> BRASSANTENNA = ITEMS.register("brass_transmitter",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
