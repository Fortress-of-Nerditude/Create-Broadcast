package com.fortressofnerditude.createbroadcast.item.custom;

import com.fortressofnerditude.createbroadcast.VoiceChatManager.BroadcastAudioPlugin;
import com.fortressofnerditude.createbroadcast.CreateBroadcast;
import com.fortressofnerditude.createbroadcast.VoiceChatManager.FrequencyManager;
import com.fortressofnerditude.createbroadcast.component.ModDataComponents;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.audiochannel.EntityAudioChannel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;
import java.util.UUID;

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
    public void verifyComponentsAfterLoad(ItemStack item) {
        item.set(ModDataComponents.ACTIVE, false);

        super.verifyComponentsAfterLoad(item);
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
            FrequencyManager.tuneOut(context.getPlayer(), context.getItemInHand().get(ModDataComponents.FREQUENCY));
        } else {
            context.getItemInHand().set(ModDataComponents.ACTIVE, true);
            FrequencyManager.tuneIn(context.getPlayer(), context.getItemInHand().get(ModDataComponents.FREQUENCY));
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
