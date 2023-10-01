package me.luligabi.coxinhautilities.common.block.dryingrack;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class DryingRackBlockEntity extends ClientSyncedBlockEntity implements Inventory {

    public DryingRackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.DRYING_RACK_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    private DefaultedList<ItemStack> inventory;
    int dryingTime;
    boolean checkedRecipe;
    boolean canDry;


    public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
        if(blockEntity.isEmpty()) return;
        if(!blockEntity.checkedRecipe) checkRecipe(blockEntity, world);

        if(blockEntity.canDry) {
            blockEntity.dryingTime++;
            craft(world, blockEntity);
            markDirty(world, blockEntity.pos, blockEntity.getCachedState());
        }
    }

    private static void checkRecipe(DryingRackBlockEntity blockEntity, World world) {
        Optional<DryingRecipe> recipeOptional = createRecipeOptional(blockEntity, world);
        if(recipeOptional.isEmpty()) return;

        blockEntity.checkedRecipe = true;
        blockEntity.canDry = recipeOptional.get().getIngredient().test(blockEntity.inventory.get(0));
        markDirty(world, blockEntity.pos, blockEntity.getCachedState());
    }

    private static void craft(World world, DryingRackBlockEntity blockEntity) {
        Optional<DryingRecipe> recipeOptional = createRecipeOptional(blockEntity, world);
        if(recipeOptional.isEmpty() || blockEntity.dryingTime < recipeOptional.get().getDryingTime()) return;


        blockEntity.inventory.set(0, recipeOptional.get().getOutput(world.getRegistryManager()));
        blockEntity.canDry = false;
        blockEntity.checkedRecipe = false;
        blockEntity.dryingTime = 0;
        markDirty(world, blockEntity.pos, blockEntity.getCachedState());
    }


    @Override
    public void toTag(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        nbt.putShort("DryingTime", (short) dryingTime);
        nbt.putBoolean("CheckedRecipe", checkedRecipe);
        nbt.putBoolean("CanDry", canDry);
    }

    @Override
    public void fromTag(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory);
        this.dryingTime = nbt.getShort("DryingTime");
        this.checkedRecipe = nbt.getBoolean("CheckedRecipe");
        this.canDry = nbt.getBoolean("CanDry");
    }

    @Override
    public void toClientTag(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void fromClientTag(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return this.inventory.get(0).isEmpty();
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    public int size() {
        return this.inventory.size();
    }

    public boolean isEmpty() {
        return this.inventory.get(0).isEmpty();
    }

    public ItemStack getStack(int slot) {
        return slot >= 0 && slot < this.inventory.size() ? this.inventory.get(slot) : ItemStack.EMPTY;
    }

    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
    }

    public void clear() {
        this.inventory.clear();
    }


    @Override
    public void markDirty() {
        super.markDirty();
        if(!isClientSide()) {
            sync();
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected static void markDirty(World world, BlockPos pos, BlockState state) {
        world.markDirty(pos);
        if (!state.isAir()) {
            world.updateComparators(pos, state.getBlock());
        }
        if(!((DryingRackBlockEntity) world.getBlockEntity(pos)).isClientSide()) {
            ((DryingRackBlockEntity) world.getBlockEntity(pos)).sync();
        }

    }

    private static Optional<DryingRecipe> createRecipeOptional(DryingRackBlockEntity blockEntity, World world) {
        return world.getServer().getRecipeManager().getFirstMatch(DryingRecipe.Type.INSTANCE, blockEntity, world);
    }

}