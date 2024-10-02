package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagRegistry {

    public static final TagKey<Block> UNBOXABLE = TagKey.create(Registries.BLOCK, CoxinhaUtilities.id("unboxable"));
    public static final TagKey<Block> CARRIER_BLACKLIST = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("carrier", "blacklist"));
    public static final TagKey<Block> ENDER_ORCHID_STRICT_PLACEMENT = TagKey.create(Registries.BLOCK, CoxinhaUtilities.id("ender_orchid_strict_placement"));

    public static void init() {
        // NO-OP
    }

    private TagRegistry() {
        // NO-OP
    }

}