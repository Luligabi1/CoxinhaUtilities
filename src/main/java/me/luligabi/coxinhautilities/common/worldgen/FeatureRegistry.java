package me.luligabi.coxinhautilities.common.worldgen;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.misc.EnderOrchidBlock;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public class FeatureRegistry { // TODO: Add config to disable and modify ender orchid worldgen

    public static void init() {
        addFeature(FeatureRegistry.ENDER_ORCHID_ID, BiomeSelectors.foundInTheEnd(), true);
    }


    private static void addFeature(String id, Predicate<BiomeSelectionContext> biomeSelector, boolean enabled) {
        if(!enabled) return;
        BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(id)));
    }


    private static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ENDER_ORCHID_CONFIGURED_FEATURE =
            ConfiguredFeatures.register(FeatureRegistry.ENDER_ORCHID_ID, Feature.FLOWER, new RandomPatchFeatureConfig(2, 7, 2,
                    PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockRegistry.ENDER_ORCHID.getDefaultState().with(EnderOrchidBlock.AGE, 7)))))
            );

    private static final RegistryEntry<PlacedFeature> ENDER_ORCHID_PLACED_FEATURE = PlacedFeatures.register(FeatureRegistry.ENDER_ORCHID_ID, ENDER_ORCHID_CONFIGURED_FEATURE,
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()
    );

    private static final String ENDER_ORCHID_ID = "ender_orchid";

}