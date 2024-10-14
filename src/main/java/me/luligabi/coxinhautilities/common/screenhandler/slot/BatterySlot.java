package me.luligabi.coxinhautilities.common.screenhandler.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;

public class BatterySlot extends Slot {

    public BatterySlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
    }

}