package me.luligabi.coxinhautilities.common;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ComponentRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.misc.lootfunction.LootFunctionRegistry;
import me.luligabi.coxinhautilities.common.recipe.RecipeRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import me.luligabi.coxinhautilities.common.worldgen.FeatureRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class CoxinhaUtilities implements ModInitializer {

    @Override
    public void onInitialize() {
        ComponentRegistry.init();
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

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    static {
        ModConfig.HANDLER.load();
        CONFIG = ModConfig.HANDLER.instance();
    }
}