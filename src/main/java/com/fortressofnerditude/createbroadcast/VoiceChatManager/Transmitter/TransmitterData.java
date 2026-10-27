package com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class TransmitterData extends SavedData {
    public static final Map<Vec3, TransmitterInfo> transmitterPositions = new HashMap<>();

    // Create new instance of saved data
    public static TransmitterData create() {
        return new TransmitterData();
    }

    // Load existing instance of saved data
    public static TransmitterData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        TransmitterData data = TransmitterData.create();

        ListTag transmitterList = tag.getList("transmitters", ListTag.TAG_COMPOUND);
        for (int i = 0; i < transmitterList.size(); i++) {
            CompoundTag transmitterStorage = transmitterList.getCompound(i);

            Vec3 position = new Vec3(transmitterStorage.getDouble("x"), transmitterStorage.getDouble("y"), transmitterStorage.getDouble("z"));
            TransmitterInfo info = new TransmitterInfo(transmitterStorage.getIntArray("info")[0], position, transmitterStorage.getIntArray("info")[2], transmitterStorage.getIntArray("info")[3]);
            transmitterPositions.put(position, info);
        }

        return data;
    }

    public void createTransmitter(Vec3 location, Integer frequency) {
        if (frequency == null) {
            frequency = 87;
        }
        transmitterPositions.put(location, new TransmitterInfo(frequency, location));
        setDirty();
    }

    public void destroyTransmitter(Vec3 location) {
        transmitterPositions.remove(location);
        setDirty();
    }

    public static Map<Vec3, TransmitterInfo> readTransmitters() {
        return transmitterPositions;
    }

    public void updateTransmitter(Vec3 location, TransmitterInfo newInfo) {
        transmitterPositions.put(location, newInfo);
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag transmitterList = new ListTag();
        transmitterPositions.forEach((position, info) -> {
            CompoundTag transmitterStorage = new CompoundTag();
            transmitterStorage.putDouble("x", position.x);
            transmitterStorage.putDouble("y", position.y);
            transmitterStorage.putDouble("z", position.z);
            transmitterStorage.putIntArray("info", info.plainDump());
            transmitterList.add(transmitterStorage);
        });
        tag.put("transmitters", transmitterList);
        return tag;
    }
}
