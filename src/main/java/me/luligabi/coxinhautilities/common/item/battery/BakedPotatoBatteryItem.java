package me.luligabi.coxinhautilities.common.item.battery;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BakedPotatoBatteryItem extends PotatoBatteryItem {

    public BakedPotatoBatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    public long getEnergyCapacity() { return 10240; }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendPowerInfo(tooltip, stack, Formatting.GOLD, Formatting.YELLOW);
        addWittyComment(tooltip);
    }

    @Override
    public List<TranslatableText> wittyComments() {
        List<TranslatableText> wittyComments = new ArrayList<>();
        wittyComments.add(new TranslatableText("tooltip.coxinhautilities.baked_potato_battery.witty.1"));
        wittyComments.add(new TranslatableText("tooltip.coxinhautilities.baked_potato_battery.witty.2"));
        return wittyComments;
    }
}