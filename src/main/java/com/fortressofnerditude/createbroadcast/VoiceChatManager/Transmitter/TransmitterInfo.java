package com.fortressofnerditude.createbroadcast.VoiceChatManager.Transmitter;

import com.fortressofnerditude.createbroadcast.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class TransmitterInfo {
    private Integer frequency = null;
    private Integer elevation = null;
    private Integer rpm = 0;
    public Integer range = 0;

    public TransmitterInfo(Integer itemFrequency, Vec3 location, Integer storedRpm, Integer storedRange) {
        frequency = itemFrequency;
        rpm = storedRpm;
        elevation = (int) location.y;
        range = storedRange;
    }

    public TransmitterInfo(Integer itemFrequency, Vec3 location) {
        frequency = itemFrequency;
        elevation = (int) location.y;
    }

    public TransmitterInfo(Vec3 location) {
        frequency = 87;
        elevation = (int) location.y;
        this.recalculateRange();
    }

    private void recalculateRange() {
        if (Config.TRANSMITTER_DIVISOR.get() == -1) {
            range = -1;
        } else {
            range = Math.round(rpm * ((float) elevation / Config.TRANSMITTER_DIVISOR.get()));
        }
    }

    public void updateRpm(Integer newRpm) {
        rpm = newRpm;
        this.recalculateRange();
    }

    public void updateElevation(Vec3 transmitterPosition, Level level) {
        int checkedElevation = (int) transmitterPosition.y;
        while (level.getBlockState(new BlockPos((int) transmitterPosition.x, (checkedElevation + 1), (int) transmitterPosition.z)).is(Blocks.LIGHTNING_ROD)) {
            checkedElevation += 1;
        }
        elevation = checkedElevation;
        this.recalculateRange();
    }

    public HashMap<String, Integer> dump() {
        HashMap<String, Integer> dataMap = new HashMap<>();
        dataMap.put("Frequency", frequency);
        dataMap.put("Elevation", elevation);
        dataMap.put("RPM", rpm);
        dataMap.put("Range", range);
        return dataMap;
    }

    public ArrayList<Integer> plainDump() {
        ArrayList<Integer> dataList = new ArrayList<>();
        dataList.add(frequency);
        dataList.add(elevation);
        dataList.add(rpm);
        dataList.add(range);
        return dataList;
    }
}
