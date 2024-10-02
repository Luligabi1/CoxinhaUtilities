package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

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
        protected long getCapacity(FluidVariant variant) { return ((PortableTankBlock) getBlockState().getBlock()).getTankTier().getCapacity(); }

        @Override
        protected void onFinalCommit() { setChanged(); }
    };

    public boolean fluidIo(Player player, InteractionHand hand) {
        return FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    public boolean hasWrittenNbt() {
        return fluidStorage.amount > 0 || !fluidStorage.isResourceBlank();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(!isClientSide()) {
            sync();
        }
    }

    @Override
    public void toTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        SingleVariantStorage.writeNbt(fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    public void fromTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
    }

    @Override
    public void toClientTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        toTag(nbt, registryLookup);
    }

    @Override
    public void fromClientTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        fromTag(nbt, registryLookup);
    }

}