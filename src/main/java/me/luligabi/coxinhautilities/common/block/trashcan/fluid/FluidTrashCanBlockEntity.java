package me.luligabi.coxinhautilities.common.block.trashcan.fluid;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.util.Util;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
public class FluidTrashCanBlockEntity extends BlockEntity {

    public FluidTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FLUID_TRASH_CAN_BLOCK_ENTITY, pos, state);
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {

        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected boolean canExtract(FluidVariant variant) {
            return false;
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return Long.MAX_VALUE;
        }

        @Override // Ignore whatever is inserted by resetting to empty fluid and 0 fluid amount
        protected void onFinalCommit() {
            fluidStorage.variant = FluidVariant.blank();
            fluidStorage.amount = 0;
        }

    };

    public ActionResult fluidIo(PlayerEntity player, Hand hand) {
        return Util.interactPlayerHand(fluidStorage, player, hand, true, false);
    }

}