package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.screenhandler.slot.BatterySlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EnergyTrashCanMenu extends AbstractContainerMenu {

    private final Container inventory;

    public EnergyTrashCanMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(1));
    }

    public EnergyTrashCanMenu(int syncId, Inventory playerInventory, Container inventory) {
        super(MenuTypeRegistry.ENERGY_TRASH_CAN.get(), syncId);
        this.inventory = inventory;
        checkContainerSize(inventory, 1);
        inventory.startOpen(playerInventory.player);


        this.addSlot(new BatterySlot(inventory, 0, 8 + 4*18,  36));

        int s;
        int i = (3 - 4) * 18;

        for(s = 0; s < 3; ++s) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + s * 9 + 9, 8 + l * 18, 103 + s * 18 + i));
            }
        }
        for(s = 0; s < 9; ++s) {
            this.addSlot(new Slot(playerInventory, s, 8 + s * 18, 161 + i));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(itemStack2, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }
}