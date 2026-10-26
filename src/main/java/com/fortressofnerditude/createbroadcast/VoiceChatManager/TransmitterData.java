package com.fortressofnerditude.createbroadcast.VoiceChatManager;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransmitterData extends SavedData {
    public static final Map<Vec3, Double> TransmitterPositions = new HashMap<>();

    // Create new instance of saved data
    public static TransmitterData create() {
        return new TransmitterData();
    }

    // Load existing instance of saved data
    public static TransmitterData load() {
        TransmitterData data = TransmitterData.create();
        // Load saved data
        return data;
    }

    public void createTransmitter(Vec3 location, Double frequency) {
        if (frequency == null) {
            frequency = 87.0;
        }
        TransmitterPositions.put(location, frequency);
        setDirty();
    }

    public void destroyTransmitter(Vec3 location) {
        TransmitterPositions.remove(location);
        setDirty();
    }

    public static Map<Vec3, Double> readTransmitters() {
        return TransmitterPositions;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        // Write data to tag
        return tag;
    }
}
