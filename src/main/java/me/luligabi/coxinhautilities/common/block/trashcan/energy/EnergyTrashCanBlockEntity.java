package me.luligabi.coxinhautilities.common.block.trashcan.energy;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.EnergyTrashCanScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

@SuppressWarnings("UnstableApiUsage")
public class EnergyTrashCanBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {

    public EnergyTrashCanBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    private DefaultedList<ItemStack> inventory;

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
        if(stackEnergy != null & stackEnergy.supportsExtraction()) {
            EnergyStorageUtil.move(stackEnergy, blockEntity.energyStorage, Long.MAX_VALUE, null);
        }
    }


    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("block.coxinhautilities.energy_trash_can");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EnergyTrashCanScreenHandler(syncId, inv, this);
    }

    private EnergyStorage getEnergyStorage(ItemStack stack) {
        return EnergyStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlot(0)));
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }

}