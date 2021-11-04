package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenHandlingRegistry {

    public static void init() {
        WOODEN_HOPPER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(CoxinhaUtilities.MOD_ID, "wooden_hopper"), WoodenHopperScreenHandler::new);
    }

    public static ScreenHandlerType<WoodenHopperScreenHandler> WOODEN_HOPPER_SCREEN_HANDLER;

}
