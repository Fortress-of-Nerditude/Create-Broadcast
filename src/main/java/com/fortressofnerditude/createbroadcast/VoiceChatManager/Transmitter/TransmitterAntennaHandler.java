package com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid= CreateBroadcast.MODID, bus = EventBusSubscriber.Bus.GAME)
public class TransmitterAntennaHandler {

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel() instanceof Level level) {
            if (level.isClientSide()) return;
            if (event.getPlacedBlock().getBlock().equals(Blocks.LIGHTNING_ROD) || event.getPlacedBlock().getBlock().equals(ModBlocks.TRANSMITTER.get())) {
                if (level.getServer() == null) return;
                TransmitterData transmitterData = level.getServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(TransmitterData::create, TransmitterData::load), "transmitters");
                Map<Vec3, TransmitterInfo> transmitters = transmitterData.readTransmitters();
                for (Map.Entry<Vec3, TransmitterInfo> entry : transmitters.entrySet()) {
                    Vec3 transmitterPosition = entry.getKey();
                    TransmitterInfo transmitterInfo = entry.getValue();
                    if (transmitterPosition.x == event.getPos().getX() && transmitterPosition.z == event.getPos().getZ()) {
                        transmitterInfo.updateElevation(transmitterPosition, level);
                        transmitterData.updateTransmitter(transmitterPosition, transmitterInfo);
                    }
                }
            }
        }
    }

}
