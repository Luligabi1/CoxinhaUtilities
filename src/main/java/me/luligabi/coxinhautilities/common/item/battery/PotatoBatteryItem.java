package me.luligabi.coxinhautilities.common.item.battery;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleBatteryItem;

import java.util.List;

public class PotatoBatteryItem extends Item implements SimpleBatteryItem {

    public PotatoBatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    public long getEnergyCapacity() { return 2048; }

    @Override
    public long getEnergyMaxInput() { return Long.MAX_VALUE; }

    @Override
    public long getEnergyMaxOutput() { return Long.MAX_VALUE; }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.potato_battery.1")
                .formatted(Formatting.GOLD)
                .append(new TranslatableText("tooltip.coxinhautilities.potato_battery.2", stack.getOrCreateNbt().getLong(ENERGY_KEY), getEnergyCapacity())
                        .formatted(Formatting.YELLOW)));
        tooltip.add(new LiteralText(" "));
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.potato_battery.3").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
    }

}