package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
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

    /*
     * This code is originally from the Modern Industrialization mod, copyrighted by Azercoco & Technici4n, licensed under the MIT license.
     *
     * You may see the original code here: https://github.com/AztechMC/Modern-Industrialization/blob/7774247aa27e908c5b798bd45892c6cedb0473e9/src/main/java/aztech/modern_industrialization/blocks/storage/tank/TankBlockEntity.java#L64
     */
    public boolean onPlayerUse(PlayerEntity player) { // TODO: Add sound when doing interactions
        Storage<FluidVariant> handIo = ContainerItemContext.ofPlayerHand(player, Hand.MAIN_HAND).find(FluidStorage.ITEM);
        if (handIo != null) {
            if (StorageUtil.move(fluidStorage, handIo, f -> true, Long.MAX_VALUE, null) > 0) {
                return true;
            }
        }
        return false;
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