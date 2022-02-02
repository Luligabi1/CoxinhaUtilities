package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.client.renderer.item.GrannysSinkItemRenderer;
import me.luligabi.coxinhautilities.client.renderer.item.PortableTankItemRenderer;
import me.luligabi.coxinhautilities.client.screen.WoodenHopperScreen;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CoxinhaUtilitiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        portableTanks.forEach(portableTanks -> BlockRenderLayerMap.INSTANCE.putBlock(portableTanks, RenderLayer.getCutoutMipped()));

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                BlockRegistry.AQUATIC_TORCH,
                BlockRegistry.WALL_AQUATIC_TORCH);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), BlockRegistry.TINTED_GLASS_PANE);

        BlockEntityRegistry.clientInit();

        portableTanks.forEach(portableTanks -> BuiltinItemRendererRegistry.INSTANCE.register(portableTanks, new PortableTankItemRenderer(portableTanks.getDefaultState())));
        BuiltinItemRendererRegistry.INSTANCE.register(BlockRegistry.GRANNYS_SINK, new GrannysSinkItemRenderer());

        ScreenRegistry.register(ScreenHandlingRegistry.WOODEN_HOPPER_SCREEN_HANDLER, WoodenHopperScreen::new);
    }

    private final List<Block> portableTanks = Arrays.asList(
            BlockRegistry.PORTABLE_TANK_MK1,
            BlockRegistry.PORTABLE_TANK_MK2,
            BlockRegistry.PORTABLE_TANK_MK3,
            BlockRegistry.PORTABLE_TANK_MK4,
            BlockRegistry.PORTABLE_TANK_MK5
    );

}