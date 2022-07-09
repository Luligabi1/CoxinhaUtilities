package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagRegistry {

    public static final TagKey<Block> UNBOXABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier(CoxinhaUtilities.MOD_ID, "unboxable"));


    public static void init() {
        // NO-OP
    }

    private TagRegistry() {
        // NO-OP
    }

}