package me.luligabi.coxinhautilities.common.block.dryingrack;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class DryingRackInventory extends SimpleContainer {

    private final DryingRackBlockEntity blockEntity;
    public final DryingRackInput input = new DryingRackInput(this);

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
    public int getMaxStackSize() {
        return 1;
    }

    public class DryingRackInput implements RecipeInput {

        private final DryingRackInventory inventory;

        protected DryingRackInput(DryingRackInventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public ItemStack getItem(int i) {
            return inventory.getItem(i);
        }

        @Override
        public int size() {
            return 1;
        }
    }
}