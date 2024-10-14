package me.luligabi.coxinhautilities.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FeatureRegistry {

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CoxinhaUtilities.MOD_ID);


    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<EnderOrchidBiomeModifier>> ENDER_ORCHID_BIOME_MODIFIER = BIOME_MODIFIERS.register(
        "ender_orchid",
        () -> RecordCodecBuilder.mapCodec(
        builder -> builder.group(
                Biome.LIST_CODEC.fieldOf("biomes").forGetter(EnderOrchidBiomeModifier::biomes),
                PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(EnderOrchidBiomeModifier::features)
            ).apply(builder, EnderOrchidBiomeModifier::new)
        )
    );


}