package me.luligabi.coxinhautilities.common.screenhandler;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.woodenhopper.WoodenHopperBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MenuTypeRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, CoxinhaUtilities.MOD_ID);


    public static final Supplier<MenuType<WoodenHopperMenu>> WOODEN_HOPPER = MENU_TYPES.register(
        "wooden_hopper",
        () -> new MenuType<>(WoodenHopperMenu::new, FeatureFlagSet.of(FeatureFlags.VANILLA))
    );

    public static final Supplier<MenuType<FluidTrashCanMenu>> FLUID_TRASH_CAN = MENU_TYPES.register(
        "fluid_trash_can",
        () -> new MenuType<>(FluidTrashCanMenu::new, FeatureFlagSet.of(FeatureFlags.VANILLA))
    );

    public static final Supplier<MenuType<EnergyTrashCanMenu>> ENERGY_TRASH_CAN = MENU_TYPES.register(
        "energy_trash_can",
        () ->  new MenuType<>(EnergyTrashCanMenu::new, FeatureFlagSet.of(FeatureFlags.VANILLA))
    );


}