package me.luligabi.coxinhautilities.common;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ComponentRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.misc.CapabilityRegistry;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import me.luligabi.coxinhautilities.common.misc.lootfunction.LootFunctionRegistry;
import me.luligabi.coxinhautilities.common.recipe.RecipeRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.MenuTypeRegistry;
import me.luligabi.coxinhautilities.common.worldgen.FeatureRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(CoxinhaUtilities.MOD_ID)
public class CoxinhaUtilities {


    public CoxinhaUtilities(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ComponentRegistry.DATA_COMPONENT_TYPES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITY_TYPES.register(modEventBus);
        RecipeRegistry.RECIPE_TYPES.register(modEventBus);
        RecipeRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        MenuTypeRegistry.MENU_TYPES.register(modEventBus);
        FeatureRegistry.BIOME_MODIFIERS.register(modEventBus);
        LootFunctionRegistry.LOOT_FUNCTION_TYPES.register(modEventBus);
        ItemGroupInit.CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(CapabilityRegistry::registerCapabilities);
    }

    @SubscribeEvent
    private void commonSetup(final FMLCommonSetupEvent event) {
    }


    public static final String MOD_ID = "coxinhautilities";
    public static final ModConfig CONFIG;

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    static {
        ModConfig.HANDLER.load(); CONFIG = ModConfig.HANDLER.instance();
    }
}