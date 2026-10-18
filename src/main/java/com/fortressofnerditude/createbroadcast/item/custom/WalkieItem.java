package com.fortressofnerditude.createbroadcast.item.custom;

import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import com.fortressofnerditude.createbroadcast.item.ModItems;
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

import java.util.List;

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
        }

        context.getItemInHand();

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
