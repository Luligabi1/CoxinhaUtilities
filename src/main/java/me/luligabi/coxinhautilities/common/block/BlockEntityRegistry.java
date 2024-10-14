package me.luligabi.coxinhautilities.common.block;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlockEntity;
import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackBlockEntity;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import me.luligabi.coxinhautilities.common.block.trashcan.energy.EnergyTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.block.trashcan.fluid.FluidTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.MOD_ID);

    // FIXME
    /*public static void init() {
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> sink.fluidStorage, BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY);


        FluidStorage.SIDED.registerForBlockEntity((trashCan, direction) -> trashCan.fluidStorage, BlockEntityRegistry.FLUID_TRASH_CAN_BLOCK_ENTITY);
        EnergyStorage.SIDED.registerForBlockEntity((trashCan, direction) -> trashCan.energyStorage, BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY);


        ItemStorage.SIDED.registerForBlockEntity((blockEntity, context) -> blockEntity.inventoryWrapper, DRYING_RACK_BLOCK_ENTITY);
    }*/

    public static final Supplier<BlockEntityType<WoodenHopperBlockEntity>> WOODEN_HOPPER_ENTITY = BLOCK_ENTITY_TYPES.register(
        "wooden_hopper",
        () -> BlockEntityType.Builder.of(WoodenHopperBlockEntity::new, BlockRegistry.WOODEN_HOPPER.get()).build(null)
    );

    public static final Supplier<BlockEntityType<PortableTankBlockEntity>> PORTABLE_TANK = BLOCK_ENTITY_TYPES.register(
        "portable_tank",
        () -> BlockEntityType.Builder.of(PortableTankBlockEntity::new,
            BlockRegistry.PORTABLE_TANK_MK1.get(),
            BlockRegistry.PORTABLE_TANK_MK2.get(),
            BlockRegistry.PORTABLE_TANK_MK3.get(),
            BlockRegistry.PORTABLE_TANK_MK4.get(),
            BlockRegistry.PORTABLE_TANK_MK5.get()
        ).build(null)
    );

    public static final Supplier<BlockEntityType<GrannysSinkBlockEntity>> GRANNYS_SINK = BLOCK_ENTITY_TYPES.register(
        "grannys_sink",
        () -> BlockEntityType.Builder.of(GrannysSinkBlockEntity::new, BlockRegistry.GRANNYS_SINK.get()).build(null)
    );

    public static final Supplier<BlockEntityType<FluidTrashCanBlockEntity>> FLUID_TRASH_CAN = BLOCK_ENTITY_TYPES.register(
        "fluid_trash_can",
        () -> BlockEntityType.Builder.of(FluidTrashCanBlockEntity::new, BlockRegistry.FLUID_TRASH_CAN.get()).build(null)
    );

    public static final Supplier<BlockEntityType<EnergyTrashCanBlockEntity>> ENERGY_TRASH_CAN = BLOCK_ENTITY_TYPES.register(
        "energy_trash_can",
        () -> BlockEntityType.Builder.of(EnergyTrashCanBlockEntity::new, BlockRegistry.ENERGY_TRASH_CAN.get()).build(null)
    );

    public static final Supplier<BlockEntityType<DryingRackBlockEntity>> DRYING_RACK = BLOCK_ENTITY_TYPES.register(
        "drying_rack",
        () -> BlockEntityType.Builder.of(DryingRackBlockEntity::new, BlockRegistry.DRYING_RACK.get()).build(null)
    );

    public static final Supplier<BlockEntityType<CardboardBoxBlockEntity>> CARDBOARD_BOX = BLOCK_ENTITY_TYPES.register(
        "cardboard_box",
        () -> BlockEntityType.Builder.of(CardboardBoxBlockEntity::new, BlockRegistry.CARDBOARD_BOX.get()).build(null)
    );

}