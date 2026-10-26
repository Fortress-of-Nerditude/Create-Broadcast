package com.fortressofnerditude.createbroadcast.block.entity;

import com.fortressofnerditude.createbroadcast.block.ModBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RadioTransmitterEntity extends KineticBlockEntity {
    public RadioTransmitterEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRANSMITTER_ENTITY.get(), pos, state);
    }
}
