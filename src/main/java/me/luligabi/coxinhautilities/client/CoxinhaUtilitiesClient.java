package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.client.screen.WoodenHopperScreen;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class CoxinhaUtilitiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ScreenHandlingRegistry.WOODEN_HOPPER_SCREEN_HANDLER, WoodenHopperScreen::new);
    }
}
