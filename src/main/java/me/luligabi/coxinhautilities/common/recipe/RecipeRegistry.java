package me.luligabi.coxinhautilities.common.recipe;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeSerializer;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeRegistry {


    public static final RecipeSerializer<DryingRecipe> DRYING_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, CoxinhaUtilities.id("drying"), new DryingRecipeSerializer());
    public static final RecipeType<DryingRecipe> DRYING_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, CoxinhaUtilities.id("drying"), DryingRecipeType.INSTANCE);

    public static void init() {
        // NO-OP
    }

    private RecipeRegistry() {
        // NO-OP
    }

}