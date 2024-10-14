package me.luligabi.coxinhautilities.common.worldgen;

import com.mojang.serialization.MapCodec;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public record EnderOrchidBiomeModifier(HolderSet<Biome> biomes, HolderSet<PlacedFeature> features) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if(!CoxinhaUtilities.CONFIG.canGenerateEnderOrchids) return;

        if(phase == Phase.ADD && this.biomes.contains(biome)) {
            BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
            features.forEach(holder -> generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, holder));
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return FeatureRegistry.ENDER_ORCHID_BIOME_MODIFIER.get();
    }

}