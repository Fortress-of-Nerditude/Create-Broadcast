package com.fortressofnerditude.createbroadcast.item.custom;

import com.fortressofnerditude.createbroadcast.BroadcastAudioPlugin;
import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.fortressofnerditude.createbroadcast.item.ModItems;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.audiochannel.EntityAudioChannel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

public class WalkieItem extends Item {
    public WalkieItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getItemInHand().get(ModDataComponents.ACTIVE) == null) {
            context.getItemInHand().set(ModDataComponents.ACTIVE, false);
        }
        if (context.getItemInHand().get(ModDataComponents.FREQUENCY) == null) {
            context.getItemInHand().set(ModDataComponents.FREQUENCY, 87.0);
        }

        if (Boolean.TRUE.equals(context.getItemInHand().get(ModDataComponents.ACTIVE))) {
            context.getItemInHand().set(ModDataComponents.ACTIVE, false);
        } else {
            context.getItemInHand().set(ModDataComponents.ACTIVE, true);
            VoicechatServerApi api = BroadcastAudioPlugin.api;
            if (BroadcastAudioPlugin.api == null) {
                CreateBroadcast.LOGGER.info("API null");
                return super.useOn(context);
            }
            UUID channel_id = new UUID(Double.doubleToRawLongBits(context.getItemInHand().get(ModDataComponents.FREQUENCY)), 0L);
            EntityAudioChannel channel = BroadcastAudioPlugin.api.createEntityAudioChannel(
                    channel_id,
                    BroadcastAudioPlugin.api.fromEntity(context.getPlayer()));
            CreateBroadcast.LOGGER.info("Channel Set");
            if (channel == null) {
                return super.useOn(context);
            }
            channel.setCategory("broadcast"); // The category of the audio channel
            channel.setDistance(48);
        }

        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.get(ModDataComponents.ACTIVE) != null) {
            tooltipComponents.add(Component.literal("Active: " + stack.get(ModDataComponents.ACTIVE)));
        }
        if (stack.get(ModDataComponents.FREQUENCY) != null) {
            tooltipComponents.add(Component.literal("Frequency: " + stack.get(ModDataComponents.FREQUENCY)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
