package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class PortableTankBlockEntity extends ClientSyncedBlockEntity {

    public PortableTankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PORTABLE_TANK.get(), pos, state);
    }

    public final FluidTank fluidStorage = new FluidTank(((PortableTankBlock) getBlockState().getBlock()).getTankTier().getCapacity());



    public boolean fluidIo(Player player, InteractionHand hand) {
        return false; //FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    public boolean hasWrittenNbt() {
        return fluidStorage.getFluid() != FluidStack.EMPTY;
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
        fluidStorage.writeToNBT(registryLookup, nbt);
    }

    @Override
    public void fromTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        fluidStorage.readFromNBT(registryLookup, nbt);
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