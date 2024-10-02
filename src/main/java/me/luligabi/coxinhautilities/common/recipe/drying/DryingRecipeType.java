package me.luligabi.coxinhautilities.common.recipe.drying;

import net.minecraft.world.item.crafting.RecipeType;

public class DryingRecipeType implements RecipeType<DryingRecipe> {

    private DryingRecipeType() {
    }

    public static final DryingRecipeType INSTANCE = new DryingRecipeType();

    public static final String ID = "drying";
}