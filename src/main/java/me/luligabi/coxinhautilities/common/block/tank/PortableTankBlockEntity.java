package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class PortableTankBlockEntity extends ClientSyncedBlockEntity {

    public PortableTankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY, pos, state);
    }

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
        return fluidStorage.amount > 0 || !fluidStorage.isResourceBlank();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(!isClientSide()) {
            sync();
        }
    }

    @Override
    public void toTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SingleVariantStorage.writeNbt(fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    public void fromTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
    }

    @Override
    public void toClientTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        toTag(nbt, registryLookup);
    }

    @Override
    public void fromClientTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        fromTag(nbt, registryLookup);
    }

}