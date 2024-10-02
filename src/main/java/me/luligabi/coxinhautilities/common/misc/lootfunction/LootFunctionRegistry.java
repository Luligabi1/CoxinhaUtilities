package me.luligabi.coxinhautilities.common.misc.lootfunction;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class LootFunctionRegistry {

    public static final LootItemFunctionType<TankCopyDataLootFunction> TANK_COPY_DATA = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, CoxinhaUtilities.id("tank_copy_data"),  new LootItemFunctionType<>(TankCopyDataLootFunction.CODEC));
    public static final LootItemFunctionType<CardboardBoxCopyDataLootFunction> CARDBOARD_BOX_COPY_DATA = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, CoxinhaUtilities.id("cardboard_box_copy_data"), new LootItemFunctionType<>(CardboardBoxCopyDataLootFunction.CODEC));


    public static void init() {
        // NO-OP
    }

    private LootFunctionRegistry() {
        // NO-OP
    }

}