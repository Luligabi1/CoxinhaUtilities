package me.luligabi.coxinhautilities.common.block.trashcan.energy;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.screenhandler.EnergyTrashCanScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    public static void tick(World world, BlockPos pos, BlockState state, EnergyTrashCanBlockEntity blockEntity) {
        ItemStack stack = blockEntity.inventory.get(0);
        if(stack.isEmpty()) return;

        EnergyStorage stackEnergy = blockEntity.getEnergyStorage(stack);
        if(stackEnergy != null && stackEnergy.supportsExtraction()) {
            EnergyStorageUtil.move(stackEnergy, blockEntity.energyStorage, Long.MAX_VALUE, null);
        }
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("block.coxinhautilities.energy_trash_can");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EnergyTrashCanScreenHandler(syncId, inv, this);
    }

    private EnergyStorage getEnergyStorage(ItemStack stack) {
        return EnergyStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0)));
    }

}