package com.fortressofnerditude.createbroadcast.block.custom;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.VoiceChatManager.TransmitterData;
import com.fortressofnerditude.createbroadcast.block.ModBlockEntities;
import com.fortressofnerditude.createbroadcast.block.entity.RadioTransmitterEntity;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import com.simibubi.create.content.kinetics.base.KineticBlock;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioTransmitter extends KineticBlock implements IBE<RadioTransmitterEntity> {

    public RadioTransmitter(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        TransmitterData.load().createTransmitter(Vec3.atLowerCornerOf(pos), stack.get(ModDataComponents.FREQUENCY));
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        TransmitterData.load().destroyTransmitter(Vec3.atLowerCornerOf(pos));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.get(ModDataComponents.FREQUENCY) != null) {
            stack.set(ModDataComponents.FREQUENCY, 87.0);
        }
        tooltipComponents.add(Component.literal("Frequency: " + stack.get(ModDataComponents.FREQUENCY)));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader level, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }

    @Override
    public Class<RadioTransmitterEntity> getBlockEntityClass() {
        return RadioTransmitterEntity.class;
    }

    @Override
    public BlockEntityType<? extends RadioTransmitterEntity> getBlockEntityType() {
        return ModBlockEntities.TRANSMITTER_ENTITY.get();
    }
}
