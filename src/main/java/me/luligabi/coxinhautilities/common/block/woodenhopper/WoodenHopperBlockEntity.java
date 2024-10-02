package me.luligabi.coxinhautilities.common.block.woodenhopper;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.WoodenHopperScreenHandler;
import me.luligabi.coxinhautilities.mixin.HopperBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class WoodenHopperBlockEntity extends HopperBlockEntity {


    public WoodenHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        ((HopperBlockEntityAccessor) this).coxinhautilities_setInventory(NonNullList.withSize(1, ItemStack.EMPTY));
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityRegistry.WOODEN_HOPPER_ENTITY;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new WoodenHopperScreenHandler(syncId, playerInventory, this);
    }

}