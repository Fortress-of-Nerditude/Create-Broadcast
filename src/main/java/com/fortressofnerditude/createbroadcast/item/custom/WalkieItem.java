package com.fortressofnerditude.createbroadcast.item.custom;

import com.fortressofnerditude.createbroadcast.VoiceChatManager.FrequencyManager;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class WalkieItem extends Item {
    public WalkieItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        item.set(ModDataComponents.ACTIVE, false);
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        playerRightClicked(context.getPlayer(), context.getItemInHand());
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        playerRightClicked(player, item);
        return InteractionResultHolder.sidedSuccess(item, level.isClientSide());
    }

    private void playerRightClicked(Player player, ItemStack item) {
        if (item.get(ModDataComponents.ACTIVE) == null) {
            item.set(ModDataComponents.ACTIVE, false);
        }
        if (item.get(ModDataComponents.FREQUENCY) == null) {
            item.set(ModDataComponents.FREQUENCY, 87.0);
        }

        if (Boolean.TRUE.equals(item.get(ModDataComponents.ACTIVE))) {
            item.set(ModDataComponents.ACTIVE, false);
            FrequencyManager.tuneOut(player, item.get(ModDataComponents.FREQUENCY));
        } else {
            item.set(ModDataComponents.ACTIVE, true);
            FrequencyManager.tuneIn(player, item.get(ModDataComponents.FREQUENCY));
        }
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
