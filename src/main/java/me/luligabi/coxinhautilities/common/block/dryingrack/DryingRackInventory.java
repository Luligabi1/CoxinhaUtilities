package me.luligabi.coxinhautilities.common.block.dryingrack;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class DryingRackInventory extends SimpleContainer implements RecipeInput {

    private final DryingRackBlockEntity blockEntity;

    public DryingRackInventory(DryingRackBlockEntity blockEntity) {
        super(1);
        this.blockEntity = blockEntity;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        blockEntity.setChanged();
    }

    @Override
    public ItemStack getItem(int slot) {
        return super.getItem(slot);
    }

    @Override
    public int size() {
        return getContainerSize();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}