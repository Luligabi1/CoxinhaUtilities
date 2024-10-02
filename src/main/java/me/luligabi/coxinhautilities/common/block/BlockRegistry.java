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
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@SuppressWarnings("SameParameterValue")
public class BlockRegistry {

    public static void init() {
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

        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id("cardboard_box"), CARDBOARD_BOX);
        Registry.register(BuiltInRegistries.ITEM, CoxinhaUtilities.id("cardboard_box"), new CardboardBoxBlockItem());
        ItemGroupInit.ITEMS.add(new ItemStack(CARDBOARD_BOX));

        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id("ender_orchid"), ENDER_ORCHID);
        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id("potted_ender_orchid"), POTTED_ENDER_ORCHID);

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

    public static final HopperBlock WOODEN_HOPPER = new WoodenHopperBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).mapColor(MapColor.WOOD));

    public static final PortableTankBlock PORTABLE_TANK_MK1 = new PortableTankBlock(TankTier.MK1);
    public static final PortableTankBlock PORTABLE_TANK_MK2 = new PortableTankBlock(TankTier.MK2);
    public static final PortableTankBlock PORTABLE_TANK_MK3 = new PortableTankBlock(TankTier.MK3);
    public static final PortableTankBlock PORTABLE_TANK_MK4 =  new PortableTankBlock(TankTier.MK4);
    public static final PortableTankBlock PORTABLE_TANK_MK5 = new PortableTankBlock(TankTier.MK5);

    public static final GrannysSinkBlock GRANNYS_SINK = new GrannysSinkBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.8F).mapColor(MapColor.TERRACOTTA_CYAN));

    public static final FluidTrashCanBlock FLUID_TRASH_CAN = new FluidTrashCanBlock();
    public static final EnergyTrashCanBlock ENERGY_TRASH_CAN = new EnergyTrashCanBlock();

    public static final DryingRackBlock DRYING_RACK = new DryingRackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));

    public static final CardboardBoxBlock CARDBOARD_BOX = new CardboardBoxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.5F).sound(SoundType.WOOD));

    public static final Block ENDER_ORCHID = new EnderOrchidBlock();
    public static final Block POTTED_ENDER_ORCHID = new FlowerPotBlock(ENDER_ORCHID, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));

    public static final Block AQUATIC_TORCH = new AquaticTorchBlock(BlockBehaviour.Properties.of().sound(SoundType.LADDER).noOcclusion().noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD));
    public static final Block WALL_AQUATIC_TORCH = new WallAquaticTorchBlock(BlockBehaviour.Properties.of().sound(SoundType.LADDER).noOcclusion().noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD));

    public static final Block COPPER_LADDER = new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED);
    public static final Block EXPOSED_COPPER_LADDER = new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED);
    public static final Block WEATHERED_COPPER_LADDER = new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED);
    public static final Block OXIDIZED_COPPER_LADDER = new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED);

    public static final Block WAXED_COPPER_LADDER = new CopperLadderBlock();
    public static final Block WAXED_EXPOSED_COPPER_LADDER = new CopperLadderBlock();
    public static final Block WAXED_WEATHERED_COPPER_LADDER = new CopperLadderBlock();
    public static final Block WAXED_OXIDIZED_COPPER_LADDER = new CopperLadderBlock();

    public static final Block LAVA_SPONGE = new LavaSpongeBlock();
    public static final Block WET_LAVA_SPONGE = new WetLavaSpongeBlock();

    //public static final Block TINTED_GLASS_PANE = new TintedPaneBlock(FabricBlockSettings.copyOf(Blocks.TINTED_GLASS));

    private static void initBlock(String identifier, Block block, Rarity rarity, boolean isHidden) {
        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id(identifier), block);
        Registry.register(BuiltInRegistries.ITEM, CoxinhaUtilities.id(identifier), new BlockItem(block, new Item.Properties().rarity(rarity)));
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
        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id(identifier), block);
        if(block != PORTABLE_TANK_MK5) {
            Registry.register(BuiltInRegistries.ITEM, CoxinhaUtilities.id(identifier), new PortableTankBlockItem(block, new Item.Properties().stacksTo(1)));
        } else {
            Registry.register(BuiltInRegistries.ITEM, CoxinhaUtilities.id(identifier), new PortableTankBlockItem(block, new Item.Properties().stacksTo(1).fireResistant()));
        }
        ItemGroupInit.ITEMS.add(new ItemStack(block));
    }

    private static void initWallStandingBlock(String identifier, Block block, Block wallBlock) {
        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id(identifier), block);
        Registry.register(BuiltInRegistries.BLOCK, CoxinhaUtilities.id("wall_" + identifier), wallBlock);
        Registry.register(BuiltInRegistries.ITEM, CoxinhaUtilities.id(identifier), new StandingAndWallBlockItem(block, wallBlock, new Item.Properties(), Direction.DOWN));

        ItemGroupInit.ITEMS.add(new ItemStack(block));
    }

}