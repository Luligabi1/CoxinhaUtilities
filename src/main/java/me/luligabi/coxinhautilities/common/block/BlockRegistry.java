package me.luligabi.coxinhautilities.common.block;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlock;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

    public static void init() {
        initBlock("wooden_hopper", WOODEN_HOPPER);
        WOODEN_HOPPER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, ""), FabricBlockEntityTypeBuilder.create(WoodenHopperBlockEntity::new, WOODEN_HOPPER).build(null));
    }

    public static final HopperBlock WOODEN_HOPPER = new WoodenHopperBlock(AbstractBlock.Settings.of(Material.STONE).hardness(3.0f));

    public static BlockEntityType<WoodenHopperBlockEntity> WOODEN_HOPPER_ENTITY;

    private static void initBlock(String identifier, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, identifier), block);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new BlockItem(block, new FabricItemSettings().group(CoxinhaUtilities.ITEM_GROUP)));
    }


}