package me.luligabi.coxinhautilities.common.block;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntityRenderer;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntityRenderer;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistry {

    public static void init() {
        WOODEN_HOPPER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "wooden_hopper"), FabricBlockEntityTypeBuilder.create(WoodenHopperBlockEntity::new, BlockRegistry.WOODEN_HOPPER).build(null));

        PORTABLE_TANK_MK1_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "portable_tank_mk1"), FabricBlockEntityTypeBuilder.create(PortableTankBlockEntity::new, BlockRegistry.PORTABLE_TANK_MK1).build(null));
        PORTABLE_TANK_MK2_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "portable_tank_mk2"), FabricBlockEntityTypeBuilder.create(PortableTankBlockEntity::new, BlockRegistry.PORTABLE_TANK_MK2).build(null));
        PORTABLE_TANK_MK3_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "portable_tank_mk3"), FabricBlockEntityTypeBuilder.create(PortableTankBlockEntity::new, BlockRegistry.PORTABLE_TANK_MK3).build(null));
        PORTABLE_TANK_MK4_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "portable_tank_mk4"), FabricBlockEntityTypeBuilder.create(PortableTankBlockEntity::new, BlockRegistry.PORTABLE_TANK_MK4).build(null));
        PORTABLE_TANK_MK5_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "portable_tank_mk5"), FabricBlockEntityTypeBuilder.create(PortableTankBlockEntity::new, BlockRegistry.PORTABLE_TANK_MK5).build(null));

        GRANNYS_SINK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CoxinhaUtilities.MOD_ID, "grannys_sink"), FabricBlockEntityTypeBuilder.create(GrannysSinkBlockEntity::new, BlockRegistry.GRANNYS_SINK).build(null));
    }

    public static void clientInit() {
        BlockEntityRendererRegistry.register(BlockEntityRegistry.PORTABLE_TANK_MK1_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.PORTABLE_TANK_MK2_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.PORTABLE_TANK_MK3_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.PORTABLE_TANK_MK4_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityRegistry.PORTABLE_TANK_MK5_BLOCK_ENTITY, PortableTankBlockEntityRenderer::new);

        BlockEntityRendererRegistry.register(BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY, GrannysSinkBlockEntityRenderer::new);
    }

    public static BlockEntityType<WoodenHopperBlockEntity> WOODEN_HOPPER_ENTITY;

    public static BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_MK1_BLOCK_ENTITY; // TODO: Change this to use only one BlockEntityType when #1699 is merged.
    public static BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_MK2_BLOCK_ENTITY;
    public static BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_MK3_BLOCK_ENTITY;
    public static BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_MK4_BLOCK_ENTITY;
    public static BlockEntityType<PortableTankBlockEntity> PORTABLE_TANK_MK5_BLOCK_ENTITY;

    public static BlockEntityType<GrannysSinkBlockEntity> GRANNYS_SINK_BLOCK_ENTITY;
}