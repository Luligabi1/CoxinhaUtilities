package me.luligabi.coxinhautilities.common.worldgen;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public class FeatureRegistry {

    public static void init() {
        addFeature(FeatureRegistry.ENDER_ORCHID, BiomeSelectors.foundInTheEnd(), CoxinhaUtilities.CONFIG.canGenerateEnderOrchids);
    }


    private static void addFeature(RegistryKey<PlacedFeature> registryKey, Predicate<BiomeSelectionContext> biomeSelector, boolean enabled) {
        if(!enabled) return;
        BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.VEGETAL_DECORATION, registryKey);
    }


    private static final RegistryKey<PlacedFeature> ENDER_ORCHID = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("coxinhautilities", "ender_orchid"));

}