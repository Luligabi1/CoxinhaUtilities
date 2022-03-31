package me.luligabi.coxinhautilities.common.recipe.drying;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DryingRecipe implements Recipe<Inventory> {

    private final Ingredient ingredient;
    private final ItemStack outputStack;
    private final int dryingTime;
    private final Identifier identifier;

    public DryingRecipe(Ingredient ingredient, ItemStack outputStack, int dryingTime, Identifier identifier) {
        this.ingredient = ingredient;
        this.outputStack = outputStack;
        this.dryingTime = dryingTime;
        this.identifier = identifier;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public ItemStack getOutput() {
        return outputStack.copy();
    }

    public int getDryingTime() {
        return dryingTime;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return outputStack.copy();
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return true;
    }

    @Override
    public boolean fits(int var1, int var2) {
        return false;
    }

    @Override
    public Identifier getId() {
        return identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DryingRecipeSerializer.INSTANCE;
    }

    public static class Type implements RecipeType<DryingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();

        public static final String ID = "drying";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
}