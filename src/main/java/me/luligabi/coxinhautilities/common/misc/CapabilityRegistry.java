package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.item.battery.PotatoBatteryItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CapabilityRegistry {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.WOODEN_HOPPER_ENTITY.get(), (block, side) -> block.invWrapper);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.PORTABLE_TANK.get(), (block, side) -> block.fluidStorage);

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.GRANNYS_SINK.get(), (block, side) -> block.fluidStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.FLUID_TRASH_CAN.get(), (block, side) -> block.invWrapper);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, BlockEntityRegistry.FLUID_TRASH_CAN.get(), (block, side) -> block.fluidStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.ENERGY_TRASH_CAN.get(), (block, side) -> block.invWrapper);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, BlockEntityRegistry.ENERGY_TRASH_CAN.get(), (block, side) -> block.energyStorage);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.DRYING_RACK.get(), (block, side) -> block.invWrapper);

        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> ((PotatoBatteryItem) stack.getItem()).energyStorage,
            ItemRegistry.POTATO_BATTERY.get(),
            ItemRegistry.BAKED_POTATO_BATTERY.get(),
            ItemRegistry.POISONOUS_POTATO_BATTERY.get()
        );
    }
}