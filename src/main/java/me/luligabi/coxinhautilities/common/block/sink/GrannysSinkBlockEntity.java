package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class GrannysSinkBlockEntity extends BlockEntity {

    public GrannysSinkBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GRANNYS_SINK.get(), pos, state);
    }

    public final FluidTank fluidStorage = new FluidTank(Integer.MAX_VALUE) {

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return new FluidStack(Fluids.WATER, Integer.MAX_VALUE);
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if(resource.getFluid() == Fluids.WATER) return resource.copy();
            return super.drain(resource, action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return new FluidStack(Fluids.WATER, maxDrain);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }


    };

    public boolean fluidIo(Player player, InteractionHand hand) {
        return false; //FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }


    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        fluidStorage.writeToNBT(registryLookup, nbt);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        fluidStorage.readFromNBT(registryLookup, nbt);
    }

}