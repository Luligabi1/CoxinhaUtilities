package me.luligabi.coxinhautilities.common.screenhandler.slot;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

@SuppressWarnings("UnstableApiUsage")
public class BatterySlot extends Slot {

    public BatterySlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) { // if this causes issues, check for SimpleBatteryItem instead
        return EnergyStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(container, null).getSlot(0))) != null;
    }

}
