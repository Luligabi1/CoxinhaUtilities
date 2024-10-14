package me.luligabi.coxinhautilities.common.item.battery;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BakedPotatoBatteryItem extends PotatoBatteryItem {

    public BakedPotatoBatteryItem(Properties settings) {
        super(settings, 10240);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        appendPowerInfo(tooltip, stack);
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(
                Component.translatable("tooltip.coxinhautilities.baked_potato_battery.witty.1"),
                Component.translatable("tooltip.coxinhautilities.baked_potato_battery.witty.2")
        );
    }

    protected ChatFormatting getPrimaryColor() {
        return ChatFormatting.GOLD;
    }

    protected ChatFormatting getSecondaryColor() {
        return ChatFormatting.YELLOW;
    }

}