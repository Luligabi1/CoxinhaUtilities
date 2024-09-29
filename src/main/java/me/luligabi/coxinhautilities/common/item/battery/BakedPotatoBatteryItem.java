package me.luligabi.coxinhautilities.common.item.battery;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BakedPotatoBatteryItem extends PotatoBatteryItem {

    public BakedPotatoBatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) { return 10240; }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendPowerInfo(tooltip, stack);
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(
                Text.translatable("tooltip.coxinhautilities.baked_potato_battery.witty.1"),
                Text.translatable("tooltip.coxinhautilities.baked_potato_battery.witty.2")
        );
    }

    protected Formatting getPrimaryColor() {
        return Formatting.GOLD;
    }

    protected Formatting getSecondaryColor() {
        return Formatting.YELLOW;
    }

}