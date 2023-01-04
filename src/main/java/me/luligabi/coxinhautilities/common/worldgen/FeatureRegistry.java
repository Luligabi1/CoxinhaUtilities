package me.luligabi.coxinhautilities.common.worldgen;

@SuppressWarnings("unused")
public class FeatureRegistry {

    public static void init() {
        //addFeature(FeatureRegistry.ENDER_ORCHID_ID, BiomeSelectors.foundInTheEnd(), CoxinhaUtilities.CONFIG.canGenerateEnderOrchids);
    }


    /*private static void addFeature(String id, Predicate<BiomeSelectionContext> biomeSelector, boolean enabled) {
        if(!enabled) return;
        BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(id)));
    }


    private static final RegistryEntry<ConfiguredFeature<RandomPatchFeatureConfig, ?>> ENDER_ORCHID_CONFIGURED_FEATURE =
            ConfiguredFeatures.register(FeatureRegistry.ENDER_ORCHID_ID, Feature.FLOWER, new RandomPatchFeatureConfig(CoxinhaUtilities.CONFIG.enderOrchidSpawnRate, 7, 2,
                    PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(BlockRegistry.ENDER_ORCHID.getDefaultState().with(EnderOrchidBlock.AGE, 7)))))
            );

    private static final RegistryEntry<PlacedFeature> ENDER_ORCHID_PLACED_FEATURE = PlacedFeatures.register(FeatureRegistry.ENDER_ORCHID_ID, ENDER_ORCHID_CONFIGURED_FEATURE,
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.of()
    );*/

    private static final String ENDER_ORCHID_ID = "ender_orchid";

}