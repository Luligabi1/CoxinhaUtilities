package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
public class AbstractTankBlockEntity extends BlockEntity {

    public AbstractTankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.TANK_BLOCK_ENTITY, pos, state);
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return 16 * FluidConstants.BUCKET;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.put("fluidVariant", fluidStorage.variant.toNbt());
        tag.putLong("amount", fluidStorage.amount);
        return super.writeNbt(tag);
    }
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        fluidStorage.variant = FluidVariant.fromNbt(tag.getCompound("fluidVariant"));
        fluidStorage.amount = tag.getLong("amount");
    }
}