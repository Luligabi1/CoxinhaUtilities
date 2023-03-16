package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenHandlingRegistry {

    public static void init() {
        WOODEN_HOPPER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(CoxinhaUtilities.MOD_ID, "wooden_hopper"), new ScreenHandlerType<>(WoodenHopperScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));

        FLUID_TRASH_CAN_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(CoxinhaUtilities.MOD_ID, "fluid_trash_can"), new ScreenHandlerType<>(FluidTrashCanScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));
        ENERGY_TRASH_CAN_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(CoxinhaUtilities.MOD_ID, "energy_trash_can"), new ScreenHandlerType<>(EnergyTrashCanScreenHandler::new, FeatureSet.empty()));
    }

    public static ScreenHandlerType<WoodenHopperScreenHandler> WOODEN_HOPPER_SCREEN_HANDLER;

    public static ScreenHandlerType<FluidTrashCanScreenHandler> FLUID_TRASH_CAN_SCREEN_HANDLER;
    public static ScreenHandlerType<EnergyTrashCanScreenHandler> ENERGY_TRASH_CAN_SCREEN_HANDLER;

}
