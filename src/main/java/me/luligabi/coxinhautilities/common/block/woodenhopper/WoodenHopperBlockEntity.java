package me.luligabi.coxinhautilities.common.block.woodenhopper;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;


public class WoodenHopperBlockEntity extends HopperBlockEntity {

    private DefaultedList<ItemStack> inventory;

    public WoodenHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockRegistry.WOODEN_HOPPER_ENTITY;
    }

    @Override // TODO: Add custom screen.
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return super.createScreenHandler(syncId, playerInventory);
    }

    @Override
    public Text getContainerName() {
        return new TranslatableText("container.coxinhautilities.wooden_hopper");
    }


}