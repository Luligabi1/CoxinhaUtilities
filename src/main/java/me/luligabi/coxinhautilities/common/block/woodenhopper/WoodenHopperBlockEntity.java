package me.luligabi.coxinhautilities.common.block.woodenhopper;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.WoodenHopperScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;


public class WoodenHopperBlockEntity extends HopperBlockEntity {

    private DefaultedList<ItemStack> inventory;
    // TODO FIXING: Inventory is still 5 internally.

    public WoodenHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockRegistry.WOODEN_HOPPER_ENTITY;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new WoodenHopperScreenHandler(syncId, playerInventory, this);
    }

}