package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.screenhandler.slot.BatterySlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class EnergyTrashCanScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public EnergyTrashCanScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public EnergyTrashCanScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlingRegistry.ENERGY_TRASH_CAN_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 1);
        inventory.onOpen(playerInventory.player);


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
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }
}