package me.luligabi.coxinhautilities.common.block;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.aquatictorch.AquaticTorchBlock;
import me.luligabi.coxinhautilities.common.block.aquatictorch.WallAquaticTorchBlock;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlock;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlockItem;
import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackBlock;
import me.luligabi.coxinhautilities.common.block.misc.CopperLadderBlock;
import me.luligabi.coxinhautilities.common.block.misc.EnderOrchidBlock;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlock;
import me.luligabi.coxinhautilities.common.block.sponge.LavaSpongeBlock;
import me.luligabi.coxinhautilities.common.block.sponge.WetLavaSpongeBlock;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlock;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockItem;
import me.luligabi.coxinhautilities.common.block.tank.TankTier;
import me.luligabi.coxinhautilities.common.block.trashcan.energy.EnergyTrashCanBlock;
import me.luligabi.coxinhautilities.common.block.trashcan.fluid.FluidTrashCanBlock;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlock;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

@SuppressWarnings("SameParameterValue")
public class BlockRegistry {

    public static void init() {
        Registry.register(Registries.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, "ender_orchid"), ENDER_ORCHID);

        initBlock("wooden_hopper", WOODEN_HOPPER);


        initPortableTankBlock("portable_tank_mk1", PORTABLE_TANK_MK1);
        initPortableTankBlock("portable_tank_mk2", PORTABLE_TANK_MK2);
        initPortableTankBlock("portable_tank_mk3", PORTABLE_TANK_MK3);
        initPortableTankBlock("portable_tank_mk4", PORTABLE_TANK_MK4);
        initPortableTankBlock("portable_tank_mk5", PORTABLE_TANK_MK5);

        initBlock("grannys_sink", GRANNYS_SINK, Rarity.UNCOMMON);

        initBlock("fluid_trash_can", FLUID_TRASH_CAN);
        initBlock("energy_trash_can", ENERGY_TRASH_CAN);

        initBlock("drying_rack", DRYING_RACK);

        Registry.register(Registries.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, "cardboard_box"), CARDBOARD_BOX);
        Registry.register(Registries.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "cardboard_box"), new CardboardBoxBlockItem());
        ItemGroupInit.ITEMS.add(new ItemStack(CARDBOARD_BOX));

        /*initBlock("overworld_cake", OVERWORLD_CAKE, Rarity.UNCOMMON); // TODO: Fix first usage triggering the Credits Screen
        initBlock("nether_cake", NETHER_CAKE, Rarity.UNCOMMON); // TODO: Fix Nether Cake being borked
        initBlock("ender_cake", ENDER_CAKE, Rarity.UNCOMMON);*/

        initWallStandingBlock("aquatic_torch", AQUATIC_TORCH, WALL_AQUATIC_TORCH);

        // Copper Ladder and it's oxidation/wax variants
        initBlock("copper_ladder", COPPER_LADDER);
        initBlock("exposed_copper_ladder", EXPOSED_COPPER_LADDER);
        initBlock("weathered_copper_ladder", WEATHERED_COPPER_LADDER);
        initBlock("oxidized_copper_ladder", OXIDIZED_COPPER_LADDER);

        initBlock("waxed_copper_ladder", WAXED_COPPER_LADDER);
        initBlock("waxed_exposed_copper_ladder", WAXED_EXPOSED_COPPER_LADDER);
        initBlock("waxed_weathered_copper_ladder", WAXED_WEATHERED_COPPER_LADDER);
        initBlock("waxed_oxidized_copper_ladder", WAXED_OXIDIZED_COPPER_LADDER);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_LADDER, EXPOSED_COPPER_LADDER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_LADDER, WEATHERED_COPPER_LADDER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_LADDER, OXIDIZED_COPPER_LADDER);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_LADDER, WAXED_COPPER_LADDER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_LADDER, WAXED_EXPOSED_COPPER_LADDER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_LADDER, WAXED_WEATHERED_COPPER_LADDER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_LADDER, WAXED_OXIDIZED_COPPER_LADDER);


        initBlock("lava_sponge", LAVA_SPONGE);
        initBlock("wet_lava_sponge", WET_LAVA_SPONGE);

        //initBlock("tinted_glass_pane", TINTED_GLASS_PANE);
    }

    public static final HopperBlock WOODEN_HOPPER = new WoodenHopperBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).mapColor(MapColor.OAK_TAN));

    public static final PortableTankBlock PORTABLE_TANK_MK1 = new PortableTankBlock(TankTier.MK1);
    public static final PortableTankBlock PORTABLE_TANK_MK2 = new PortableTankBlock(TankTier.MK2);
    public static final PortableTankBlock PORTABLE_TANK_MK3 = new PortableTankBlock(TankTier.MK3);
    public static final PortableTankBlock PORTABLE_TANK_MK4 =  new PortableTankBlock(TankTier.MK4);
    public static final PortableTankBlock PORTABLE_TANK_MK5 = new PortableTankBlock(TankTier.MK5);

    public static final GrannysSinkBlock GRANNYS_SINK = new GrannysSinkBlock(FabricBlockSettings.create().requiresTool().strength(0.8F).mapColor(MapColor.TERRACOTTA_CYAN));

    public static final FluidTrashCanBlock FLUID_TRASH_CAN = new FluidTrashCanBlock();
    public static final EnergyTrashCanBlock ENERGY_TRASH_CAN = new EnergyTrashCanBlock();

    public static final DryingRackBlock DRYING_RACK = new DryingRackBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS));

    public static final CardboardBoxBlock CARDBOARD_BOX = new CardboardBoxBlock();

    //public static final Block OVERWORLD_CAKE = new DimensionalCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL), World.OVERWORLD);
    //public static final Block NETHER_CAKE = new NetherCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL));
    //public static final Block ENDER_CAKE = new DimensionalCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5F).sounds(BlockSoundGroup.WOOL), World.END);

    public static final Block ENDER_ORCHID = new EnderOrchidBlock();

    public static final Block AQUATIC_TORCH = new AquaticTorchBlock();
    public static final Block WALL_AQUATIC_TORCH = new WallAquaticTorchBlock();

    public static final Block COPPER_LADDER = new CopperLadderBlock(Oxidizable.OxidationLevel.UNAFFECTED);
    public static final Block EXPOSED_COPPER_LADDER = new CopperLadderBlock(Oxidizable.OxidationLevel.EXPOSED);
    public static final Block WEATHERED_COPPER_LADDER = new CopperLadderBlock(Oxidizable.OxidationLevel.WEATHERED);
    public static final Block OXIDIZED_COPPER_LADDER = new CopperLadderBlock(Oxidizable.OxidationLevel.OXIDIZED);

    public static final Block WAXED_COPPER_LADDER = new CopperLadderBlock();
    public static final Block WAXED_EXPOSED_COPPER_LADDER = new CopperLadderBlock();
    public static final Block WAXED_WEATHERED_COPPER_LADDER = new CopperLadderBlock();
    public static final Block WAXED_OXIDIZED_COPPER_LADDER = new CopperLadderBlock();

    public static final Block LAVA_SPONGE = new LavaSpongeBlock();
    public static final Block WET_LAVA_SPONGE = new WetLavaSpongeBlock();

    //public static final Block TINTED_GLASS_PANE = new TintedPaneBlock(FabricBlockSettings.copyOf(Blocks.TINTED_GLASS));

    private static void initBlock(String identifier, Block block, Rarity rarity, boolean isHidden) {
        Registry.register(Registries.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, identifier), block);
        Registry.register(Registries.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new BlockItem(block, new FabricItemSettings().rarity(rarity)));
        if(!isHidden) {
            ItemGroupInit.ITEMS.add(new ItemStack(block));
        }
    }

    @SuppressWarnings("unused")
    private static void initBlock(String identifier, Block block, boolean isHidden) {
        initBlock(identifier, block, Rarity.COMMON, isHidden);
    }

    private static void initBlock(String identifier, Block block, Rarity rarity) {
        initBlock(identifier, block, rarity, false);
    }

    private static void initBlock(String identifier, Block block) {
        initBlock(identifier, block, Rarity.COMMON, false);
    }

    private static void initPortableTankBlock(String identifier, PortableTankBlock block) {
        Registry.register(Registries.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, identifier), block);
        if(block != PORTABLE_TANK_MK5) {
            Registry.register(Registries.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new PortableTankBlockItem(block, new FabricItemSettings().maxCount(1)));
        } else {
            Registry.register(Registries.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new PortableTankBlockItem(block, new FabricItemSettings().maxCount(1).fireproof()));
        }
        ItemGroupInit.ITEMS.add(new ItemStack(block));
    }

    private static void initWallStandingBlock(String identifier, Block block, Block wallBlock) {
        Registry.register(Registries.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, identifier), block);
        Registry.register(Registries.BLOCK, new Identifier(CoxinhaUtilities.MOD_ID, "wall_" + identifier), wallBlock);
        Registry.register(Registries.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, identifier), new VerticallyAttachableBlockItem(block, wallBlock, new FabricItemSettings(), Direction.DOWN));

        ItemGroupInit.ITEMS.add(new ItemStack(block));
    }

}