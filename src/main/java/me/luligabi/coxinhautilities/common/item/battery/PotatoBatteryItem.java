package me.luligabi.coxinhautilities.common.item.battery;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleBatteryItem;

import java.util.List;

public class PotatoBatteryItem extends Item implements SimpleBatteryItem, IWittyComment {

    public PotatoBatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    public long getEnergyCapacity() { return 4096; }

    @Override
    public long getEnergyMaxInput() { return Long.MAX_VALUE; }

    @Override
    public long getEnergyMaxOutput() { return Long.MAX_VALUE; }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendPowerInfo(tooltip, stack, Formatting.YELLOW, Formatting.YELLOW);
        addWittyComment(tooltip);
    }

    protected void appendPowerInfo(List<Text> tooltip, ItemStack stack, Formatting primaryColor, Formatting secondaryColor) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.potato_battery.1")
                .formatted(primaryColor)
                .append(new TranslatableText("tooltip.coxinhautilities.potato_battery.2", stack.getOrCreateNbt().getLong(ENERGY_KEY), getEnergyCapacity())
                        .formatted(secondaryColor)));
    }

    @Override
    public List<TranslatableText> wittyComments() {
        return List.of(new TranslatableText("tooltip.coxinhautilities.potato_battery.witty"));
    }

}