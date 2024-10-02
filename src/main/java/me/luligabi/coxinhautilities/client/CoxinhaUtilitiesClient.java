package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.client.renderer.item.GrannysSinkItemRenderer;
import me.luligabi.coxinhautilities.client.renderer.item.PortableTankItemRenderer;
import me.luligabi.coxinhautilities.client.screen.WoodenHopperScreen;
import me.luligabi.coxinhautilities.client.screen.trashcan.EnergyTrashCanScreen;
import me.luligabi.coxinhautilities.client.screen.trashcan.FluidTrashCanScreen;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CoxinhaUtilitiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        portableTanks.forEach(portableTanks -> BlockRenderLayerMap.INSTANCE.putBlock(portableTanks, RenderType.cutoutMipped()));

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                BlockRegistry.ENDER_ORCHID,
                BlockRegistry.POTTED_ENDER_ORCHID,

                BlockRegistry.AQUATIC_TORCH,
                BlockRegistry.WALL_AQUATIC_TORCH,

                BlockRegistry.COPPER_LADDER,
                BlockRegistry.EXPOSED_COPPER_LADDER,
                BlockRegistry.WEATHERED_COPPER_LADDER,
                BlockRegistry.OXIDIZED_COPPER_LADDER,

                BlockRegistry.WAXED_COPPER_LADDER,
                BlockRegistry.WAXED_EXPOSED_COPPER_LADDER,
                BlockRegistry.WAXED_WEATHERED_COPPER_LADDER,
                BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER
        );

        //BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), BlockRegistry.TINTED_GLASS_PANE);

        BlockEntityRegistry.clientInit();

        portableTanks.forEach(portableTanks -> BuiltinItemRendererRegistry.INSTANCE.register(portableTanks, new PortableTankItemRenderer(portableTanks.defaultBlockState())));
        BuiltinItemRendererRegistry.INSTANCE.register(BlockRegistry.GRANNYS_SINK, new GrannysSinkItemRenderer());

        MenuScreens.register(ScreenHandlingRegistry.WOODEN_HOPPER_SCREEN_HANDLER, WoodenHopperScreen::new);

        MenuScreens.register(ScreenHandlingRegistry.FLUID_TRASH_CAN_SCREEN_HANDLER, FluidTrashCanScreen::new);
        MenuScreens.register(ScreenHandlingRegistry.ENERGY_TRASH_CAN_SCREEN_HANDLER, EnergyTrashCanScreen::new);
    }

    private final List<Block> portableTanks = Arrays.asList(
            BlockRegistry.PORTABLE_TANK_MK1,
            BlockRegistry.PORTABLE_TANK_MK2,
            BlockRegistry.PORTABLE_TANK_MK3,
            BlockRegistry.PORTABLE_TANK_MK4,
            BlockRegistry.PORTABLE_TANK_MK5
    );

}