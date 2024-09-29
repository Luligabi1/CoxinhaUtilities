package me.luligabi.coxinhautilities.common.block.dryingrack;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public class DryingRackInventory extends SimpleInventory implements RecipeInput {

    private final DryingRackBlockEntity blockEntity;

    public DryingRackInventory(DryingRackBlockEntity blockEntity) {
        super(1);
        this.blockEntity = blockEntity;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        blockEntity.markDirty();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return getStack(slot);
    }

    @Override
    public int getSize() {
        return size();
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }
}