package me.luligabi.coxinhautilities.common.block.woodenhopper;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.WoodenHopperScreenHandler;
import me.luligabi.coxinhautilities.mixin.HopperBlockEntityAccessor;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;


public class WoodenHopperBlockEntity extends HopperBlockEntity {


    public WoodenHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        ((HopperBlockEntityAccessor) this).coxinhautilities_setInventory(DefaultedList.ofSize(1, ItemStack.EMPTY));
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityRegistry.WOODEN_HOPPER_ENTITY;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new WoodenHopperScreenHandler(syncId, playerInventory, this);
    }

}