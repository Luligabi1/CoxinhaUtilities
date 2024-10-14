package me.luligabi.coxinhautilities.common.block.dryingrack;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.ClientSyncedBlockEntity;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.Optional;

public class DryingRackBlockEntity extends ClientSyncedBlockEntity {

    public DryingRackBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.DRYING_RACK.get(), pos, state);
    }

    int dryingTime;
    boolean checkedRecipe;
    boolean canDry;
    public final DryingRackInventory inventory = new DryingRackInventory(this);
    public final InvWrapper invWrapper = new InvWrapper(inventory);

    @SuppressWarnings("unused")
    public static void tick(Level world, BlockPos pos, BlockState state, DryingRackBlockEntity blockEntity) {
        if(blockEntity.inventory.isEmpty()) return;
        if(!blockEntity.checkedRecipe) checkRecipe(blockEntity, world);

        if(blockEntity.canDry) {
            blockEntity.dryingTime++;
            craft(world, blockEntity);
            setChanged(world, blockEntity.worldPosition, blockEntity.getBlockState());
        }
    }

    private static void checkRecipe(DryingRackBlockEntity blockEntity, Level world) {
        Optional<RecipeHolder<DryingRecipe>> recipeOptional = createRecipeOptional(blockEntity, (ServerLevel) world);
        if(recipeOptional.isEmpty()) return;

        blockEntity.checkedRecipe = true;
        blockEntity.canDry = recipeOptional.get().value().getIngredient().test(blockEntity.inventory.getItem(0));
        setChanged(world, blockEntity.worldPosition, blockEntity.getBlockState());
    }

    private static void craft(Level world, DryingRackBlockEntity blockEntity) {
        Optional<RecipeHolder<DryingRecipe>> recipeOptional = createRecipeOptional(blockEntity, (ServerLevel) world);
        if(recipeOptional.isEmpty() || blockEntity.dryingTime < recipeOptional.get().value().getDryingTime()) return;


        blockEntity.inventory.setItem(0, recipeOptional.get().value().getResultItem(world.registryAccess()));
        blockEntity.canDry = false;
        blockEntity.checkedRecipe = false;
        blockEntity.dryingTime = 0;
        setChanged(world, blockEntity.worldPosition, blockEntity.getBlockState());
    }


    @Override
    public void toTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        ContainerHelper.saveAllItems(nbt, inventory.getItems(), registryLookup);
        nbt.putShort("DryingTime", (short) dryingTime);
        nbt.putBoolean("CheckedRecipe", checkedRecipe);
        nbt.putBoolean("CanDry", canDry);
    }

    @Override
    public void fromTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        inventory.clearContent();
        ContainerHelper.loadAllItems(nbt, inventory.getItems(), registryLookup);
        dryingTime = nbt.getShort("DryingTime");
        checkedRecipe = nbt.getBoolean("CheckedRecipe");
        canDry = nbt.getBoolean("CanDry");
    }

    @Override
    public void toClientTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        ContainerHelper.saveAllItems(nbt, inventory.getItems(), registryLookup);
    }

    @Override
    public void fromClientTag(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        inventory.clearContent();
        ContainerHelper.loadAllItems(nbt, inventory.getItems(), registryLookup);
    }

    public ItemStack getStack() {
        return inventory.getItem(0);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if(level != null && !isClientSide()) sync();
    }

    @SuppressWarnings("ConstantConditions")
    protected static void setChanged(Level world, BlockPos pos, BlockState state) {
        world.blockEntityChanged(pos);
        if (!state.isAir()) {
            world.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
        if(!((DryingRackBlockEntity) world.getBlockEntity(pos)).isClientSide()) {
            ((DryingRackBlockEntity) world.getBlockEntity(pos)).sync();
        }

    }

    private static Optional<RecipeHolder<DryingRecipe>> createRecipeOptional(DryingRackBlockEntity blockEntity, ServerLevel world) {
        return world.getServer().getRecipeManager().getRecipeFor(DryingRecipeType.INSTANCE, blockEntity.inventory.input, world);
    }

}