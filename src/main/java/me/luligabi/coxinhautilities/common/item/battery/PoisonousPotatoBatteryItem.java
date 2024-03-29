package me.luligabi.coxinhautilities.common.item.battery;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PoisonousPotatoBatteryItem extends PotatoBatteryItem {

    public PoisonousPotatoBatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) { return 45056; }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendPowerInfo(tooltip, stack);
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(
                Text.translatable("tooltip.coxinhautilities.poisonous_potato_battery.witty.1"),
                Text.translatable("tooltip.coxinhautilities.poisonous_potato_battery.witty.2")
        );
    }

    protected Formatting getPrimaryColor() {
        return Formatting.DARK_GREEN;
    }

    protected Formatting getSecondaryColor() {
        return Formatting.GREEN;
    }

}