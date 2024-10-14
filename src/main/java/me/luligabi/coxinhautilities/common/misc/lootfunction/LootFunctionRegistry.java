package me.luligabi.coxinhautilities.common.misc.lootfunction;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class LootFunctionRegistry {

    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(BuiltInRegistries.LOOT_FUNCTION_TYPE, CoxinhaUtilities.MOD_ID);


    public static final Supplier<LootItemFunctionType<TankCopyDataLootFunction>> TANK_COPY_DATA = LOOT_FUNCTION_TYPES.register(
        "tank_copy_data",
        () -> new LootItemFunctionType<>(TankCopyDataLootFunction.CODEC)
    );

    public static final Supplier<LootItemFunctionType<CardboardBoxCopyDataLootFunction>> CARDBOARD_BOX_COPY_DATA = LOOT_FUNCTION_TYPES.register(
        "cardboard_box_copy_data",
        () -> new LootItemFunctionType<>(CardboardBoxCopyDataLootFunction.CODEC)
    );

}