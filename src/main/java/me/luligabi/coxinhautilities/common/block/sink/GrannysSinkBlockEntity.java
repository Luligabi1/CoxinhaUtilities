package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class GrannysSinkBlockEntity extends BlockEntity {

    public GrannysSinkBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY, pos, state);
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {

        @Override
        protected FluidVariant getBlankVariant() { return FluidVariant.of(Fluids.WATER); }

        @Override
        public FluidVariant getResource() {
            return FluidVariant.of(Fluids.WATER);
        }

        @Override
        public long getAmount() {
            return Long.MAX_VALUE;
        }

        @Override
        public long getCapacity() {
            return getAmount();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return getAmount();
        }

        @Override
        public boolean isResourceBlank() {
            return getResource().isBlank();
        }

        @Override
        public long extract(FluidVariant resource, long maxAmount, TransactionContext ctx) {
            StoragePreconditions.notBlankNotNegative(resource, maxAmount);

            if (resource.equals(getResource())) {
                return maxAmount;
            } else {
                return 0;
            }
        }

        @Override
        public long getVersion() {
            return 0;
        }

        @Override
        protected boolean canInsert(FluidVariant variant) {
            return false;
        }

    };

    public boolean fluidIo(Player player, InteractionHand hand) {
        return FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
    }


    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        SingleVariantStorage.writeNbt(fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }

}