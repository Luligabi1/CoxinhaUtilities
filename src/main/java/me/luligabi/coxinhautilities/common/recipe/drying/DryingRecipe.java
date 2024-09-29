package me.luligabi.coxinhautilities.common.recipe.drying;

import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackInventory;
import me.luligabi.coxinhautilities.common.recipe.RecipeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

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
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return getOutput();
    }

    public int getDryingTime() {
        return dryingTime;
    }

    @Override
    public ItemStack craft(DryingRackInventory input, RegistryWrapper.WrapperLookup lookup) {
        return outputStack.copy();
    }

    @Override
    public boolean matches(DryingRackInventory input, World world) {
        return ingredient.test(input.getStackInSlot(0));
    }

    @Override
    public boolean fits(int var1, int var2) {
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
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
}