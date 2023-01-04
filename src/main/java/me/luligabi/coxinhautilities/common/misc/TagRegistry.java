package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagRegistry {

    public static final TagKey<Block> UNBOXABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, "unboxable"));
    public static final TagKey<Block> CARRIER_BLACKLIST = TagKey.of(RegistryKeys.BLOCK, new Identifier("carrier", "blacklist"));


    public static void init() {
        // NO-OP
    }

    private TagRegistry() {
        // NO-OP
    }

}