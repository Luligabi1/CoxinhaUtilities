package me.luligabi.coxinhautilities.common.block.dryingrack;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeType;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class DryingRackBlockEntity extends ClientSyncedBlockEntity {

    public DryingRackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.DRYING_RACK_BLOCK_ENTITY, pos, state);
    }

    int dryingTime;
    boolean checkedRecipe;
    boolean canDry;
    public final DryingRackInventory inventory = new DryingRackInventory(this);
    public final InventoryStorage inventoryWrapper = InventoryStorage.of(inventory, null);

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
        if(blockEntity.inventory.isEmpty()) return;
        if(!blockEntity.checkedRecipe) checkRecipe(blockEntity, world);

        if(blockEntity.canDry) {
            blockEntity.dryingTime++;
            craft(world, blockEntity);
            markDirty(world, blockEntity.pos, blockEntity.getCachedState());
        }
    }

    private static void checkRecipe(DryingRackBlockEntity blockEntity, World world) {
        Optional<RecipeEntry<DryingRecipe>> recipeOptional = createRecipeOptional(blockEntity, (ServerWorld) world);
        if(recipeOptional.isEmpty()) return;

        blockEntity.checkedRecipe = true;
        blockEntity.canDry = recipeOptional.get().value().getIngredient().test(blockEntity.inventory.getStack(0));
        markDirty(world, blockEntity.pos, blockEntity.getCachedState());
    }

    private static void craft(World world, DryingRackBlockEntity blockEntity) {
        Optional<RecipeEntry<DryingRecipe>> recipeOptional = createRecipeOptional(blockEntity, (ServerWorld) world);
        if(recipeOptional.isEmpty() || blockEntity.dryingTime < recipeOptional.get().value().getDryingTime()) return;


        blockEntity.inventory.setStack(0, recipeOptional.get().value().getResult(world.getRegistryManager()));
        blockEntity.canDry = false;
        blockEntity.checkedRecipe = false;
        blockEntity.dryingTime = 0;
        markDirty(world, blockEntity.pos, blockEntity.getCachedState());
    }


    @Override
    public void toTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, inventory.heldStacks, registryLookup);
        nbt.putShort("DryingTime", (short) dryingTime);
        nbt.putBoolean("CheckedRecipe", checkedRecipe);
        nbt.putBoolean("CanDry", canDry);
    }

    @Override
    public void fromTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        inventory.clear();
        Inventories.readNbt(nbt, inventory.heldStacks, registryLookup);
        dryingTime = nbt.getShort("DryingTime");
        checkedRecipe = nbt.getBoolean("CheckedRecipe");
        canDry = nbt.getBoolean("CanDry");
    }

    @Override
    public void toClientTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, inventory.heldStacks, registryLookup);
    }

    @Override
    public void fromClientTag(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        inventory.clear();
        Inventories.readNbt(nbt, inventory.heldStacks, registryLookup);
    }

    public ItemStack getStack() {
        return inventory.getStack(0);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(world != null && !isClientSide()) sync();
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

    private static Optional<RecipeEntry<DryingRecipe>> createRecipeOptional(DryingRackBlockEntity blockEntity, ServerWorld world) {
        return world.getServer().getRecipeManager().getFirstMatch(DryingRecipeType.INSTANCE, blockEntity.inventory, world);
    }

}