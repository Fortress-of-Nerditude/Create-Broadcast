package com.fortressofnerditude.createbroadcast.block.renderer;

import com.fortressofnerditude.createbroadcast.block.entity.RadioTransmitterEntity;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class RadioTransmitterRenderer extends KineticBlockEntityRenderer<RadioTransmitterEntity> {
    public RadioTransmitterRenderer(BlockEntityRendererProvider.Context ctx) { super(ctx); };

    @Override
    protected SuperByteBuffer getRotatedModel(RadioTransmitterEntity entity, BlockState state) {
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state, Direction.DOWN);
    }
}
