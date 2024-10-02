package me.luligabi.coxinhautilities.common.block;

import me.luligabi.coxinhautilities.client.renderer.blockentity.DryingRackBlockEntityRenderer;
import me.luligabi.coxinhautilities.client.renderer.blockentity.GrannysSinkBlockEntityRenderer;
import me.luligabi.coxinhautilities.client.renderer.blockentity.PortableTankBlockEntityRenderer;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlockEntity;
import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackBlockEntity;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import me.luligabi.coxinhautilities.common.block.trashcan.energy.EnergyTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.block.trashcan.fluid.FluidTrashCanBlockEntity;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class BlockEntityRegistry {

    public static void init() {
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> sink.fluidStorage, BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY);


        FluidStorage.SIDED.registerForBlockEntity((trashCan, direction) -> trashCan.fluidStorage, BlockEntityRegistry.FLUID_TRASH_CAN_BLOCK_ENTITY);
        EnergyStorage.SIDED.registerForBlockEntity((trashCan, direction) -> trashCan.energyStorage, BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY);


        ItemStorage.SIDED.registerForBlockEntity((blockEntity, context) -> blockEntity.inventoryWrapper, DRYING_RACK_BLOCK_ENTITY);
    }

    public static void clientInit() {
        BlockEntityRenderers.register(BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);

        BlockEntityRenderers.register(BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY, GrannysSinkBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegistry.DRYING_RACK_BLOCK_ENTITY, DryingRackBlockEntityRenderer::new);
    }

    public static final BlockEntityType<WoodenHopperBlockEntity> WOODEN_HOPPER_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.id("wooden_hopper"), BlockEntityType.Builder.of(WoodenHopperBlockEntity::new, BlockRegistry.WOODEN_HOPPER).build(null));

    public static final BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
        CoxinhaUtilities.id("portable_tank"),
        BlockEntityType.Builder.of(PortableTankBlockEntity::new,
            BlockRegistry.PORTABLE_TANK_MK1,
            BlockRegistry.PORTABLE_TANK_MK2,
            BlockRegistry.PORTABLE_TANK_MK3,
            BlockRegistry.PORTABLE_TANK_MK4,
            BlockRegistry.PORTABLE_TANK_MK5
        ).build(null)
    );

    public static final BlockEntityType<GrannysSinkBlockEntity> GRANNYS_SINK_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.id("grannys_sink"), BlockEntityType.Builder.of(GrannysSinkBlockEntity::new, BlockRegistry.GRANNYS_SINK).build(null));

    public static final BlockEntityType<FluidTrashCanBlockEntity> FLUID_TRASH_CAN_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.id("fluid_trash_can"), BlockEntityType.Builder.of(FluidTrashCanBlockEntity::new, BlockRegistry.FLUID_TRASH_CAN).build(null));

    public static final BlockEntityType<EnergyTrashCanBlockEntity> ENERGY_TRASH_CAN_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.id("energy_trash_can"), BlockEntityType.Builder.of(EnergyTrashCanBlockEntity::new, BlockRegistry.ENERGY_TRASH_CAN).build(null));

    public static final BlockEntityType<DryingRackBlockEntity> DRYING_RACK_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.id("drying_rack"), BlockEntityType.Builder.of(DryingRackBlockEntity::new, BlockRegistry.DRYING_RACK).build(null));

    public static final BlockEntityType<CardboardBoxBlockEntity> CARDBOARD_BOX_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CoxinhaUtilities.id("cardboard_box"), BlockEntityType.Builder.of(CardboardBoxBlockEntity::new, BlockRegistry.CARDBOARD_BOX).build(null));
}