package me.luligabi.coxinhautilities.common.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class WoodenHopperScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public WoodenHopperScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public WoodenHopperScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlingRegistry.WOODEN_HOPPER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        checkSize(inventory, 1);
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 44 + 2 * 18, 20));


        int s;
        // Player Inventory & Hotbar
        for(s = 0; s < 3; ++s) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + s * 9 + 9, 8 + l * 18, s * 18 + 51));
            }
        }
        for(s = 0; s < 9; ++s) {
            this.addSlot(new Slot(playerInventory, s, 8 + s * 18, 109));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
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

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}