package me.luligabi.coxinhautilities.common.recipe.drying;

import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackInventory;
import me.luligabi.coxinhautilities.common.recipe.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class DryingRecipe implements Recipe<DryingRackInventory> {

    private final Ingredient ingredient;
    private final int dryingTime;
    private final ItemStack outputStack;

    public DryingRecipe(Ingredient ingredient, int dryingTime, ItemStack outputStack) {
        this.ingredient = ingredient;
        this.dryingTime = dryingTime;
        this.outputStack = outputStack;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStack getOutput() {
        return outputStack.copy();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registriesLookup) {
        return getOutput();
    }

    public int getDryingTime() {
        return dryingTime;
    }

    @Override
    public ItemStack assemble(DryingRackInventory input, HolderLookup.Provider lookup) {
        return outputStack.copy();
    }

    @Override
    public boolean matches(DryingRackInventory input, Level world) {
        return ingredient.test(input.getItem(0));
    }

    @Override
    public boolean canCraftInDimensions(int var1, int var2) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.DRYING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.DRYING_RECIPE_TYPE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}