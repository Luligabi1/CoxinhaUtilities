package me.luligabi.coxinhautilities.common.recipe;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeRegistry {

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, DryingRecipeSerializer.ID, DryingRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, DryingRecipe.Type.ID), DryingRecipe.Type.INSTANCE);
    }
}
