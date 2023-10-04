package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
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

    public boolean fluidIo(PlayerEntity player, Hand hand) {
        return FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
    }

}