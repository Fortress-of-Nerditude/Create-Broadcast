package com.fortressofnerditude.createbroadcast.block.entity;

import com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter.TransmitterData;
import com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter.TransmitterInfo;
import com.fortressofnerditude.createbroadcast.block.ModBlockEntities;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class RadioTransmitterEntity extends KineticBlockEntity {
    public RadioTransmitterEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRANSMITTER_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (level.isClientSide()) return;
        TransmitterData transmitterData = serverLevel.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(TransmitterData::create, TransmitterData::load), "transmitters");
        Map<Vec3, TransmitterInfo> transmitters = TransmitterData.readTransmitters();
        Vec3 entityPosition = Vec3.atLowerCornerOf(this.worldPosition);
        if (transmitters.containsKey(entityPosition)) {
            TransmitterInfo transmitterInfo = transmitters.get(entityPosition);
            transmitterInfo.updateRpm((int) getSpeed());
            transmitterData.updateTransmitter(entityPosition, transmitterInfo);
        }
    }
}
