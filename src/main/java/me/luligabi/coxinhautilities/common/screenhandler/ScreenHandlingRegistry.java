package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ScreenHandlingRegistry {

    public static void init() {
        WOODEN_HOPPER_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(CoxinhaUtilities.MOD_ID, "wooden_hopper"), new ScreenHandlerType<>(WoodenHopperScreenHandler::new));

        ENERGY_TRASH_CAN_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(CoxinhaUtilities.MOD_ID, "energy_trash_can"), new ScreenHandlerType<>(EnergyTrashCanScreenHandler::new));
    }

    public static ScreenHandlerType<WoodenHopperScreenHandler> WOODEN_HOPPER_SCREEN_HANDLER;

    public static ScreenHandlerType<EnergyTrashCanScreenHandler> ENERGY_TRASH_CAN_SCREEN_HANDLER;

}
