package me.luligabi.coxinhautilities.common.recipe;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

    public static void init() {
        Registry.register(Registry.RECIPE_SERIALIZER, DryingRecipeSerializer.ID, DryingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, DryingRecipe.Type.ID), DryingRecipe.Type.INSTANCE);
    }
}
