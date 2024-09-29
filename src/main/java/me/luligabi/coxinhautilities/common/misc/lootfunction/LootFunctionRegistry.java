package me.luligabi.coxinhautilities.common.misc.lootfunction;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LootFunctionRegistry {

    public static final LootFunctionType<TankCopyDataLootFunction> TANK_COPY_DATA = Registry.register(Registries.LOOT_FUNCTION_TYPE, CoxinhaUtilities.id("tank_copy_data"),  new LootFunctionType<>(TankCopyDataLootFunction.CODEC));
    public static final LootFunctionType<CardboardBoxCopyDataLootFunction> CARDBOARD_BOX_COPY_DATA = Registry.register(Registries.LOOT_FUNCTION_TYPE, CoxinhaUtilities.id("cardboard_box_copy_data"), new LootFunctionType<>(CardboardBoxCopyDataLootFunction.CODEC));


    public static void init() {
        // NO-OP
    }

    private LootFunctionRegistry() {
        // NO-OP
    }

}