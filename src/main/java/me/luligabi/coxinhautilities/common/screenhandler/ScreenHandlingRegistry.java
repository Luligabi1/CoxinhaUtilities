package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ScreenHandlingRegistry {

    public static void init() {
        WOODEN_HOPPER_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, CoxinhaUtilities.id("wooden_hopper"), new MenuType<>(WoodenHopperScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));

        FLUID_TRASH_CAN_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU, CoxinhaUtilities.id("fluid_trash_can"), new MenuType<>(FluidTrashCanScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));
        ENERGY_TRASH_CAN_SCREEN_HANDLER = Registry.register(BuiltInRegistries.MENU,CoxinhaUtilities.id("energy_trash_can"), new MenuType<>(EnergyTrashCanScreenHandler::new, FeatureFlagSet.of()));
    }

    public static MenuType<WoodenHopperScreenHandler> WOODEN_HOPPER_SCREEN_HANDLER;

    public static MenuType<FluidTrashCanScreenHandler> FLUID_TRASH_CAN_SCREEN_HANDLER;
    public static MenuType<EnergyTrashCanScreenHandler> ENERGY_TRASH_CAN_SCREEN_HANDLER;

}