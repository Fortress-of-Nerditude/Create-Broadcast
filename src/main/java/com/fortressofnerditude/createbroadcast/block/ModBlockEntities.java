package com.fortressofnerditude.createbroadcast.block;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.block.entity.RadioTransmitterEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateBroadcast.MODID);

    public static final Supplier<BlockEntityType<RadioTransmitterEntity>> TRANSMITTER_ENTITY =
            BLOCK_ENTITIES.register("radio_transmitter_entity", () -> BlockEntityType.Builder.of(
                    RadioTransmitterEntity::new, ModBlocks.TRANSMITTER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
