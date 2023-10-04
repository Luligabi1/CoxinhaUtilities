package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import me.luligabi.coxinhautilities.common.util.Util;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlockEntity extends ClientSyncedBlockEntity {

    public PortableTankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY, pos, state);
    }

    //private boolean isBucketMode = false; // TODO: Reimplement bucket mode whenever it's more stable

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {

        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) { return ((PortableTankBlock) getCachedState().getBlock()).getTankTier().getCapacity(); }

        @Override
        protected void onFinalCommit() { markDirty(); }
    };

    public boolean fluidIo(PlayerEntity player, Hand hand) {
        return FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    public boolean hasWrittenNbt() {
        return fluidStorage.amount > 0 || !fluidStorage.isResourceBlank() /*|| isBucketMode*/;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(!isClientSide()) {
            sync();
        }
    }

    @Override
    public void toTag(NbtCompound nbt) {
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        //nbt.putBoolean("isBucketMode", isBucketMode);
    }

    @Override
    public void fromTag(NbtCompound nbt) {
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
        //isBucketMode = nbt.getBoolean("isBucketMode");
    }

    @Override
    public void toClientTag(NbtCompound nbt) {
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        //nbt.putBoolean("isBucketMode", isBucketMode);
    }

    @Override
    public void fromClientTag(NbtCompound nbt) {
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
        //isBucketMode = nbt.getBoolean("isBucketMode");
    }

}