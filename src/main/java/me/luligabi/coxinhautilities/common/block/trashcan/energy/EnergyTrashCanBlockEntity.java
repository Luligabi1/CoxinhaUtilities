package me.luligabi.coxinhautilities.common.block.trashcan.energy;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.screenhandler.EnergyTrashCanScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

@SuppressWarnings("UnstableApiUsage")
public class EnergyTrashCanBlockEntity extends AbstractTrashCanBlockEntity {

    public EnergyTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY, pos, state);
    }


    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(Long.MAX_VALUE, Long.MAX_VALUE, 0) {

        @Override
        public boolean supportsExtraction() {
            return false;
        }

        @Override
        public long extract(long maxAmount, TransactionContext transaction) {
            return 0;
        }

        @Override
        protected void onFinalCommit() {
            energyStorage.amount = 0;
        }
    };

    public static void tick(Level world, BlockPos pos, BlockState state, EnergyTrashCanBlockEntity blockEntity) {
        ItemStack stack = blockEntity.inventory.get(0);
        if(stack.isEmpty()) return;

        EnergyStorage stackEnergy = blockEntity.getEnergyStorage(stack);
        if(stackEnergy != null && stackEnergy.supportsExtraction()) {
            EnergyStorageUtil.move(stackEnergy, blockEntity.energyStorage, Long.MAX_VALUE, null);
        }
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.coxinhautilities.energy_trash_can");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new EnergyTrashCanScreenHandler(syncId, inv, this);
    }

    private EnergyStorage getEnergyStorage(ItemStack stack) {
        return EnergyStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0)));
    }

}