package me.luligabi.coxinhautilities.common.block.trashcan.fluid;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.screenhandler.FluidTrashCanScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

@SuppressWarnings("UnstableApiUsage")
public class FluidTrashCanBlockEntity extends AbstractTrashCanBlockEntity {

    public FluidTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FLUID_TRASH_CAN_BLOCK_ENTITY, pos, state);
    }


    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {

        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected boolean canExtract(FluidVariant variant) {
            return false;
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return Long.MAX_VALUE;
        }

        @Override // Ignore whatever is inserted by resetting to empty fluid and 0 fluid amount
        protected void onFinalCommit() {
            fluidStorage.variant = FluidVariant.blank();
            fluidStorage.amount = 0;
        }

    };

    public static void tick(Level world, BlockPos pos, BlockState state, FluidTrashCanBlockEntity blockEntity) {
        ItemStack stack = blockEntity.inventory.get(0);
        if(stack.isEmpty()) return;

        if(blockEntity.getFluidStorage(stack) != null) {
            try (Transaction transaction = Transaction.openOuter()) {
                Storage<FluidVariant> fluidStorage = blockEntity.getFluidStorage(stack);
                Iterator<? extends StorageView<FluidVariant>> storageViewIterator = fluidStorage.iterator();

                if (storageViewIterator.hasNext()) {
                    StorageView<FluidVariant> fluidStorageView = storageViewIterator.next();

                    if (!fluidStorageView.isResourceBlank()) {
                        fluidStorage.extract(fluidStorageView.getResource(), Long.MAX_VALUE, transaction);
                    }
                }
                transaction.commit();
            }
        }
    }

    public boolean fluidIo(Player player, InteractionHand hand) {
        return FluidStorageUtil.interactWithFluidStorage(fluidStorage, player, hand);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.coxinhautilities.fluid_trash_can");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new FluidTrashCanScreenHandler(syncId, inv, this);
    }

    @Nullable
    private Storage<FluidVariant> getFluidStorage(ItemStack stack) {
        return FluidStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0)));
    }

}