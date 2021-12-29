package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlockEntity extends BlockEntity {

    public PortableTankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.PORTABLE_TANK_MK1_BLOCK_ENTITY, pos, state);
    }

    private boolean isBucketMode = false;

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) { return ((PortableTankBlock) getCachedState().getBlock()).getTankTier().getCapacity(); }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    /*
     * This code is originally from the Modern Industrialization mod, copyrighted by Azercoco & Technici4n, licensed under the MIT license.
     *
     * You may see the original code here: https://github.com/AztechMC/Modern-Industrialization/blob/7774247aa27e908c5b798bd45892c6cedb0473e9/src/main/java/aztech/modern_industrialization/blocks/storage/tank/TankBlockEntity.java#L64
     */
    public boolean onPlayerUse(PlayerEntity player) { // TODO: Add sound when doing interactions
        Storage<FluidVariant> handIo = ContainerItemContext.ofPlayerHand(player, Hand.MAIN_HAND).find(FluidStorage.ITEM);
        if (handIo != null) {
            // Item -> Tank action
            if (StorageUtil.move(handIo, fluidStorage, f -> true, Long.MAX_VALUE, null) > 0) {
                /*player.getWorld().playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);*/
                return true;
            }
            // Tank -> Item action
            if (StorageUtil.move(fluidStorage, handIo, f -> true, Long.MAX_VALUE, null) > 0) {
                /*player.getWorld().playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL,
                        SoundCategory.BLOCKS, 1.0F, 1.0F);*/
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        nbt.putBoolean("isBucketMode", isBucketMode);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
        isBucketMode = nbt.getBoolean("isBucketMode");
    }
}