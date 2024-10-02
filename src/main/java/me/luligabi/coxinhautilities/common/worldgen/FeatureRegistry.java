package me.luligabi.coxinhautilities.common.worldgen;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public class FeatureRegistry {

    public static void init() {
        addFeature(FeatureRegistry.ENDER_ORCHID, BiomeSelectors.foundInTheEnd(), CoxinhaUtilities.CONFIG.canGenerateEnderOrchids);
    }


    private static void addFeature(ResourceKey<PlacedFeature> registryKey, Predicate<BiomeSelectionContext> biomeSelector, boolean enabled) {
        if(!enabled) return;
        BiomeModifications.addFeature(biomeSelector, GenerationStep.Decoration.VEGETAL_DECORATION, registryKey);
    }


    private static final ResourceKey<PlacedFeature> ENDER_ORCHID = ResourceKey.create(Registries.PLACED_FEATURE, CoxinhaUtilities.id("ender_orchid"));

}