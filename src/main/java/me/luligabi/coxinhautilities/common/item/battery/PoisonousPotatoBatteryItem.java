package me.luligabi.coxinhautilities.common.item.battery;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PoisonousPotatoBatteryItem extends PotatoBatteryItem {

    public PoisonousPotatoBatteryItem(Properties settings) {
        super(settings);
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) { return 45056; }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        appendPowerInfo(tooltip, stack);
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(
                Component.translatable("tooltip.coxinhautilities.poisonous_potato_battery.witty.1"),
                Component.translatable("tooltip.coxinhautilities.poisonous_potato_battery.witty.2")
        );
    }

    protected ChatFormatting getPrimaryColor() {
        return ChatFormatting.DARK_GREEN;
    }

    protected ChatFormatting getSecondaryColor() {
        return ChatFormatting.GREEN;
    }

}