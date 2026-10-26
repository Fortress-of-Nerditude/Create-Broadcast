package com.fortressofnerditude.createbroadcast.client;

import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.block.ModBlockEntities;
import com.fortressofnerditude.createbroadcast.block.renderer.RadioTransmitterRenderer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CreateBroadcast.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreateBroadcastClient {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.TRANSMITTER_ENTITY.get(), RadioTransmitterRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
            SimpleBlockEntityVisualizer.builder(ModBlockEntities.TRANSMITTER_ENTITY.get())
                .factory((context, entity, point) -> new OrientedRotatingVisual<>(
                    context, entity, point, Direction.SOUTH, Direction.DOWN,
                    Models.partial(AllPartialModels.SHAFT_HALF)))
                .apply());
    }
}
