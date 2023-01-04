package me.luligabi.coxinhautilities.common.lootfunction;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LootFunctionRegistry {

    public static final LootFunctionType TANK_COPY_DATA = Registry.register(Registries.LOOT_FUNCTION_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "tank_copy_data"), new LootFunctionType(new TankCopyDataLootFunction.TankCopyDataLootFunctionSerializer()));
    public static final LootFunctionType CARDBOARD_BOX_COPY_DATA = Registry.register(Registries.LOOT_FUNCTION_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "cardboard_box_copy_data"), new LootFunctionType(new CardboardBoxCopyDataLootFunction.CardboardBoxCopyDataLootFunctionSerializer()));


    public static void init() {
        // NO-OP
    }

    private LootFunctionRegistry() {
        // NO-OP
    }

}