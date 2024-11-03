package me.luligabi.coxinhautilities.common.block.trashcan.energy;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.screenhandler.EnergyTrashCanMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class EnergyTrashCanBlockEntity extends AbstractTrashCanBlockEntity {

    public EnergyTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ENERGY_TRASH_CAN.get(), pos, state);
    }

    public final IEnergyStorage energyStorage = new IEnergyStorage() {

        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            return toReceive;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return 0;
        }

        @Override
        public int getMaxEnergyStored() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    };


    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    public static void tick(Level world, BlockPos pos, BlockState state, EnergyTrashCanBlockEntity blockEntity) {
        ItemStack stack = blockEntity.inventory.get(0);
        if(stack.isEmpty()) return;

        IEnergyStorage stackEnergy = blockEntity.getEnergyStorage(stack);
        if(stackEnergy != null && stackEnergy.canExtract()) {
            stackEnergy.extractEnergy(stackEnergy.getMaxEnergyStored(), false);
        }
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.coxinhautilities.energy_trash_can");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new EnergyTrashCanMenu(syncId, inv, this);
    }

    private IEnergyStorage getEnergyStorage(ItemStack stack) {
        return stack.getCapability(Capabilities.EnergyStorage.ITEM);
    }

}