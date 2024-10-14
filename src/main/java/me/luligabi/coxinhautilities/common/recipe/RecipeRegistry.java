package me.luligabi.coxinhautilities.common.recipe;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeSerializer;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CoxinhaUtilities.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CoxinhaUtilities.MOD_ID);


    public static final Supplier<RecipeSerializer<DryingRecipe>> DRYING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(
        "drying",
        DryingRecipeSerializer::new
    );
    public static final Supplier<RecipeType<DryingRecipe>> DRYING_RECIPE_TYPE = RECIPE_TYPES.register(
        "drying",
        () -> DryingRecipeType.INSTANCE
    );

}