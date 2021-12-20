package me.luligabi.coxinhautilities.common.block;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.cake.DimensionalCakeBlock;
import me.luligabi.coxinhautilities.common.block.cake.NetherCakeBlock;
import me.luligabi.coxinhautilities.common.block.sponge.LavaSpongeBlock;
import me.luligabi.coxinhautilities.common.block.sponge.WetLavaSpongeBlock;
import me.luligabi.coxinhautilities.common.block.tank.AbstractTankBlock;
import me.luligabi.coxinhautilities.common.block.tank.AbstractTankBlockEntity;
import me.luligabi.coxinhautilities.common.block.tank.AbstractTankBlockItem;
import me.luligabi.coxinhautilities.common.block.tank.TankTier;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlock;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BlockRegistry {

    public static void init() {
        initBlock("wooden_hopper", WOODEN_HOPPER, Rarity.COMMON);
        WOODEN_HOPPER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "wooden_hopper"), FabricBlockEntityTypeBuilder.create(WoodenHopperBlockEntity::new, WOODEN_HOPPER).build(null));

        initTankBlock("tank", TANK_BLOCK);
        TANK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "tank"), FabricBlockEntityTypeBuilder.create(AbstractTankBlockEntity::new, TANK_BLOCK).build(null));

        initBlock("overworld_cake", OVERWORLD_CAKE, Rarity.UNCOMMON); // TODO: Fix first usage triggering the Credits Screen
        initBlock("nether_cake", NETHER_CAKE, Rarity.UNCOMMON); // TODO: Fix Nether Cake being borked
        initBlock("ender_cake", ENDER_CAKE, Rarity.UNCOMMON);

        initBlock("lava_sponge", LAVA_SPONGE_BLOCK, Rarity.COMMON);
        initBlock("wet_lava_sponge", WET_LAVA_SPONGE_BLOCK, Rarity.COMMON);

        initBlock("tinted_glass_pane", TINTED_GLASS_PANE, Rarity.COMMON);
    }

    public static final HopperBlock WOODEN_HOPPER = new WoodenHopperBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static BlockEntityType<WoodenHopperBlockEntity> WOODEN_HOPPER_ENTITY;

    public static final AbstractTankBlock TANK_BLOCK = new AbstractTankBlock(TankTier.MK2, FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static BlockEntityType<AbstractTankBlockEntity> TANK_BLOCK_ENTITY;

    public static final Block OVERWORLD_CAKE = new DimensionalCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL), World.OVERWORLD);
    public static final Block NETHER_CAKE = new NetherCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL));
    public static final Block ENDER_CAKE = new DimensionalCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL), World.END);

    public static final Block LAVA_SPONGE_BLOCK = new LavaSpongeBlock(FabricBlockSettings.of(Material.SPONGE).strength(0.6F).sounds(BlockSoundGroup.GRASS));
    public static final Block WET_LAVA_SPONGE_BLOCK = new WetLavaSpongeBlock(FabricBlockSettings.of(Material.SPONGE).strength(0.6F).sounds(BlockSoundGroup.GRASS));

    public static final Block TINTED_GLASS_PANE = new TintedPaneBlock(FabricBlockSettings.copyOf(Blocks.TINTED_GLASS));

    private static void initBlock(String identifier, Block block, Rarity rarity) {
        Registry.register(Registry.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, identifier), block);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new BlockItem(block, new FabricItemSettings().rarity(rarity).group(CoxinhaUtilities.ITEM_GROUP)));
    }

    private static void initTankBlock(String identifier, AbstractTankBlock block) {
        Registry.register(Registry.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, identifier), block);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new AbstractTankBlockItem(block, new FabricItemSettings().group(CoxinhaUtilities.ITEM_GROUP)));
    }
}