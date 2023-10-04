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
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

public class BlockEntityRegistry {

    @SuppressWarnings("UnstableApiUsage")
    public static void init() {
        WOODEN_HOPPER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "wooden_hopper"), FabricBlockEntityTypeBuilder.create(WoodenHopperBlockEntity::new, BlockRegistry.WOODEN_HOPPER).build(null));

        PORTABLE_TANK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(CoxinhaUtilities.MOD_ID, "portable_tank"),
                FabricBlockEntityTypeBuilder.create(PortableTankBlockEntity::new,
                        BlockRegistry.PORTABLE_TANK_MK1,
                        BlockRegistry.PORTABLE_TANK_MK2,
                        BlockRegistry.PORTABLE_TANK_MK3,
                        BlockRegistry.PORTABLE_TANK_MK4,
                        BlockRegistry.PORTABLE_TANK_MK5
                ).build(null)
        );
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY);

        GRANNYS_SINK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "grannys_sink"), FabricBlockEntityTypeBuilder.create(GrannysSinkBlockEntity::new, BlockRegistry.GRANNYS_SINK).build(null));
        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> sink.fluidStorage, BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY);


        FLUID_TRASH_CAN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "fluid_trash_can"), FabricBlockEntityTypeBuilder.create(FluidTrashCanBlockEntity::new, BlockRegistry.FLUID_TRASH_CAN).build(null));
        FluidStorage.SIDED.registerForBlockEntity((trashCan, direction) -> trashCan.fluidStorage, BlockEntityRegistry.FLUID_TRASH_CAN_BLOCK_ENTITY);

        ENERGY_TRASH_CAN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "energy_trash_can"), FabricBlockEntityTypeBuilder.create(EnergyTrashCanBlockEntity::new, BlockRegistry.ENERGY_TRASH_CAN).build(null));
        EnergyStorage.SIDED.registerForBlockEntity((trashCan, direction) -> trashCan.energyStorage, BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY);


        DRYING_RACK_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "drying_rack"), FabricBlockEntityTypeBuilder.create(DryingRackBlockEntity::new, BlockRegistry.DRYING_RACK).build(null));
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, context) -> blockEntity.inventoryWrapper, DRYING_RACK_BLOCK_ENTITY);

        CARDBOARD_BOX_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "cardboard_box"), FabricBlockEntityTypeBuilder.create(CardboardBoxBlockEntity::new, BlockRegistry.CARDBOARD_BOX).build(null));
    }

    public static void clientInit() {
        BlockEntityRendererFactories.register(BlockEntityRegistry.PORTABLE_TANK_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY, GrannysSinkBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityRegistry.DRYING_RACK_BLOCK_ENTITY, DryingRackBlockEntityRenderer::new);
    }

    public static BlockEntityType<WoodenHopperBlockEntity> WOODEN_HOPPER_ENTITY;

    public static BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_BLOCK_ENTITY;

    public static BlockEntityType<GrannysSinkBlockEntity> GRANNYS_SINK_BLOCK_ENTITY;

    public static BlockEntityType<FluidTrashCanBlockEntity> FLUID_TRASH_CAN_BLOCK_ENTITY;
    public static BlockEntityType<EnergyTrashCanBlockEntity> ENERGY_TRASH_CAN_BLOCK_ENTITY;

    public static BlockEntityType<DryingRackBlockEntity> DRYING_RACK_BLOCK_ENTITY;

    public static BlockEntityType<CardboardBoxBlockEntity> CARDBOARD_BOX_BLOCK_ENTITY;
}