package me.luligabi.coxinhautilities.common.recipe;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeSerializer;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeRegistry {


    public static final RecipeSerializer<DryingRecipe> DRYING_RECIPE_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CoxinhaUtilities.id("drying"), new DryingRecipeSerializer());
    public static final RecipeType<DryingRecipe> DRYING_RECIPE_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, CoxinhaUtilities.id("drying"), DryingRecipeType.INSTANCE);

    public static void init() {
        // NO-OP
    }

    private RecipeRegistry() {
        // NO-OP
    }

}