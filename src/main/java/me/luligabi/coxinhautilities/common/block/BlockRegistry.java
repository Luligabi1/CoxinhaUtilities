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
import me.luligabi.coxinhautilities.common.block.tank.TankTier;
import me.luligabi.coxinhautilities.common.block.trashcan.energy.EnergyTrashCanBlock;
import me.luligabi.coxinhautilities.common.block.trashcan.fluid.FluidTrashCanBlock;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlock;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, CoxinhaUtilities.MOD_ID);


    public static final Supplier<Block> WOODEN_HOPPER = BLOCKS.register(
        "wooden_hopper",
        () -> new WoodenHopperBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).mapColor(MapColor.WOOD))
    );
    public static final Supplier<Item> WOODEN_HOPPER_ITEM = ItemRegistry.ITEMS.register(
        "wooden_hopper",
        () -> new BlockItem(WOODEN_HOPPER.get(), new Item.Properties())
    );

    public static final Supplier<Block> PORTABLE_TANK_MK1 = BLOCKS.register(
        "portable_tank_mk1",
        () -> new PortableTankBlock(TankTier.MK1)
    );
    public static final Supplier<Item> PORTABLE_TANK_MK1_ITEM = ItemRegistry.ITEMS.register(
        "portable_tank_mk1",
        () -> new BlockItem(PORTABLE_TANK_MK1.get(), new Item.Properties())
    );

    public static final Supplier<Block> PORTABLE_TANK_MK2 = BLOCKS.register(
        "portable_tank_mk2",
        () -> new PortableTankBlock(TankTier.MK2)
    );
    public static final Supplier<Item> PORTABLE_TANK_MK2_ITEM = ItemRegistry.ITEMS.register(
        "portable_tank_mk2",
        () -> new BlockItem(PORTABLE_TANK_MK2.get(), new Item.Properties())
    );

    public static final Supplier<Block> PORTABLE_TANK_MK3 = BLOCKS.register(
        "portable_tank_mk3",
        () -> new PortableTankBlock(TankTier.MK3)
    );
    public static final Supplier<Item> PORTABLE_TANK_MK3_ITEM = ItemRegistry.ITEMS.register(
        "portable_tank_mk3",
        () -> new BlockItem(PORTABLE_TANK_MK3.get(), new Item.Properties())
    );

    public static final Supplier<Block> PORTABLE_TANK_MK4 = BLOCKS.register(
        "portable_tank_mk4",
        () -> new PortableTankBlock(TankTier.MK4)
    );
    public static final Supplier<Item> PORTABLE_TANK_MK4_ITEM = ItemRegistry.ITEMS.register(
        "portable_tank_mk4",
        () -> new BlockItem(PORTABLE_TANK_MK4.get(), new Item.Properties())
    );

    public static final Supplier<Block> PORTABLE_TANK_MK5 = BLOCKS.register(
        "portable_tank_mk5",
        () -> new PortableTankBlock(TankTier.MK5)
    );
    public static final Supplier<Item> PORTABLE_TANK_MK5_ITEM = ItemRegistry.ITEMS.register(
        "portable_tank_mk5",
        () -> new BlockItem(PORTABLE_TANK_MK5.get(), new Item.Properties().fireResistant())
    );


    public static final Supplier<Block> GRANNYS_SINK = BLOCKS.register(
        "grannys_sink",
        () -> new GrannysSinkBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(0.8F).mapColor(MapColor.TERRACOTTA_CYAN))
    );
    public static final Supplier<Item> GRANNYS_SINK_ITEM = ItemRegistry.ITEMS.register(
        "grannys_sink",
        () -> new BlockItem(GRANNYS_SINK.get(), new Item.Properties())
    );

    public static final Supplier<Block> FLUID_TRASH_CAN = BLOCKS.register(
        "fluid_trash_can",
        FluidTrashCanBlock::new
    );
    public static final Supplier<Item> FLUID_TRASH_CAN_ITEM = ItemRegistry.ITEMS.register(
        "fluid_trash_can",
        () -> new BlockItem(FLUID_TRASH_CAN.get(), new Item.Properties())
    );

    public static final Supplier<Block> ENERGY_TRASH_CAN = BLOCKS.register(
        "energy_trash_can",
        EnergyTrashCanBlock::new
    );
    public static final Supplier<Item> ENERGY_TRASH_CAN_ITEM = ItemRegistry.ITEMS.register(
        "energy_trash_can",
        () -> new BlockItem(ENERGY_TRASH_CAN.get(), new Item.Properties())
    );

    public static final Supplier<Block> DRYING_RACK = BLOCKS.register(
        "drying_rack",
        () -> new DryingRackBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))
    );
    public static final Supplier<Item> DRYING_RACK_ITEM = ItemRegistry.ITEMS.register(
        "drying_rack",
        () -> new BlockItem(DRYING_RACK.get(), new Item.Properties())
    );

    public static final Supplier<Block> CARDBOARD_BOX = BLOCKS.register(
        "cardboard_box",
        () -> new CardboardBoxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(0.5F).sound(SoundType.WOOD))
    );
    public static final Supplier<Item> CARDBOARD_BOX_ITEM = ItemRegistry.ITEMS.register(
        "cardboard_box",
        CardboardBoxBlockItem::new
    );

    public static final Supplier<Block> ENDER_ORCHID = BLOCKS.register(
        "ender_orchid",
        EnderOrchidBlock::new
    );
    // FIXME
    public static final Supplier<Block> POTTED_ENDER_ORCHID = BLOCKS.register(
        "potted_ender_orchid",
        () -> new FlowerPotBlock(null, ENDER_ORCHID, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY))
    );

    public static final Supplier<Block> AQUATIC_TORCH = BLOCKS.register(
        "aquatic_torch",
        () -> new AquaticTorchBlock(BlockBehaviour.Properties.of().sound(SoundType.LADDER).noOcclusion().noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD))
    );
    public static final Supplier<Block> WALL_AQUATIC_TORCH = BLOCKS.register(
        "wall_aquatic_torch",
        () -> new WallAquaticTorchBlock(BlockBehaviour.Properties.of().sound(SoundType.LADDER).noOcclusion().noCollission().instabreak().lightLevel((state) -> 10).sound(SoundType.WOOD))
    );
    public static final Supplier<Item> AQUATIC_TORCH_ITEM = ItemRegistry.ITEMS.register(
        "aquatic_torch",
        () -> new StandingAndWallBlockItem(AQUATIC_TORCH.get(), WALL_AQUATIC_TORCH.get(), new Item.Properties(), Direction.DOWN)
    );


    public static final Supplier<Block> COPPER_LADDER = BLOCKS.register(
        "copper_ladder",
        () -> new CopperLadderBlock(WeatheringCopper.WeatherState.UNAFFECTED)
    );
    public static final Supplier<Item> COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "copper_ladder",
        () -> new BlockItem(COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> EXPOSED_COPPER_LADDER = BLOCKS.register(
        "exposed_copper_ladder",
        () -> new CopperLadderBlock(WeatheringCopper.WeatherState.EXPOSED)
    );
    public static final Supplier<Item> EXPOSED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "exposed_copper_ladder",
        () -> new BlockItem(EXPOSED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> WEATHERED_COPPER_LADDER = BLOCKS.register(
        "weathered_copper_ladder",
        () -> new CopperLadderBlock(WeatheringCopper.WeatherState.WEATHERED)
    );
    public static final Supplier<Item> WEATHERED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "weathered_copper_ladder",
        () -> new BlockItem(WEATHERED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> OXIDIZED_COPPER_LADDER = BLOCKS.register(
        "oxidized_copper_ladder",
        () -> new CopperLadderBlock(WeatheringCopper.WeatherState.OXIDIZED)
    );
    public static final Supplier<Item> OXIDIZED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "oxidized_copper_ladder",
        () -> new BlockItem(OXIDIZED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> WAXED_COPPER_LADDER = BLOCKS.register(
        "waxed_copper_ladder",
        () -> new CopperLadderBlock()
    );
    public static final Supplier<Item> WAXED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "waxed_copper_ladder",
        () -> new BlockItem(WAXED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> WAXED_EXPOSED_COPPER_LADDER = BLOCKS.register(
        "waxed_exposed_copper_ladder",
        () -> new CopperLadderBlock()
    );
    public static final Supplier<Item> WAXED_EXPOSED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "waxed_exposed_copper_ladder",
        () -> new BlockItem(WAXED_EXPOSED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> WAXED_WEATHERED_COPPER_LADDER = BLOCKS.register(
        "waxed_weathered_copper_ladder",
        () -> new CopperLadderBlock()
    );
    public static final Supplier<Item> WAXED_WEATHERED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "waxed_weathered_copper_ladder",
        () -> new BlockItem(WAXED_WEATHERED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> WAXED_OXIDIZED_COPPER_LADDER = BLOCKS.register(
        "waxed_oxidized_copper_ladder",
        () -> new CopperLadderBlock()
    );
    public static final Supplier<Item> WAXED_OXIDIZED_COPPER_LADDER_ITEM = ItemRegistry.ITEMS.register(
        "waxed_oxidized_copper_ladder",
        () -> new BlockItem(WAXED_OXIDIZED_COPPER_LADDER.get(), new Item.Properties())
    );

    public static final Supplier<Block> LAVA_SPONGE = BLOCKS.register(
        "lava_sponge",
        LavaSpongeBlock::new
    );
    public static final Supplier<Item> LAVA_SPONGE_ITEM = ItemRegistry.ITEMS.register(
        "lava_sponge",
        () -> new BlockItem(LAVA_SPONGE.get(), new Item.Properties())
    );

    public static final Supplier<Block> WET_LAVA_SPONGE = BLOCKS.register(
        "wet_lava_sponge",
        WetLavaSpongeBlock::new
    );
    public static final Supplier<Item> WET_LAVA_SPONGE_ITEM = ItemRegistry.ITEMS.register(
        "wet_lava_sponge",
        () -> new BlockItem(WET_LAVA_SPONGE.get(), new Item.Properties())
    );

}