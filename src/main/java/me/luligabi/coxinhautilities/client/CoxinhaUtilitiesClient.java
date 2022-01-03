package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.client.screen.WoodenHopperScreen;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class CoxinhaUtilitiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                BlockRegistry.PORTABLE_TANK_MK1,
                BlockRegistry.PORTABLE_TANK_MK2,
                BlockRegistry.PORTABLE_TANK_MK3,
                BlockRegistry.PORTABLE_TANK_MK4,
                BlockRegistry.PORTABLE_TANK_MK5,
                BlockRegistry.TINTED_GLASS_PANE);

        BlockEntityRegistry.clientInit();

        ScreenRegistry.register(ScreenHandlingRegistry.WOODEN_HOPPER_SCREEN_HANDLER, WoodenHopperScreen::new);
    }
}