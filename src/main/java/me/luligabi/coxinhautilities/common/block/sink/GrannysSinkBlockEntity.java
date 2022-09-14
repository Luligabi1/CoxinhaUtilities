package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
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

        /*
         * We'll use getResource as a sort of constructor for the Storage, as it's checked upon
         * creation of the BE, but not soon enough that the fluidStorage does not exist yet.
         * Then, we set the Storage's fluid amount to Long's MAX_VALUE, ensuring it'll never end.
         */
        @Override
        public FluidVariant getResource() {
            fluidStorage.amount = Long.MAX_VALUE;
            return FluidVariant.of(Fluids.WATER);
        }

        @Override
        protected boolean canInsert(FluidVariant variant) { return false; }

        @Override
        protected long getCapacity(FluidVariant variant) { return Long.MAX_VALUE; }

        @Override // Set fluid amount back to Long.MAX_VALUE after every transaction
        protected void onFinalCommit() {
            fluidStorage.amount = Long.MAX_VALUE;
            markDirty();
        }

    };

    public boolean fluidIo(PlayerEntity player, Hand hand) {
        return FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand) && !player.world.isClient();
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