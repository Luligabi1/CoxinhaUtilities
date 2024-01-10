package me.luligabi.coxinhautilities.common;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.lootfunction.LootFunctionRegistry;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.recipe.RecipeRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import me.luligabi.coxinhautilities.common.worldgen.FeatureRegistry;
import net.fabricmc.api.ModInitializer;

public class CoxinhaUtilities implements ModInitializer {

    @Override
    public void onInitialize() {
        ItemRegistry.init();

        BlockRegistry.init();
        BlockEntityRegistry.init();

        RecipeRegistry.init();
        ScreenHandlingRegistry.init();

        FeatureRegistry.init();
        TagRegistry.init();
        LootFunctionRegistry.init();
        ItemGroupInit.init();
    }


    public static final String MOD_ID = "coxinhautilities";
    public static final ModConfig CONFIG;

    static {
        ModConfig.HANDLER.load();
        CONFIG = ModConfig.HANDLER.instance();
    }
}