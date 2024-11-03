package me.luligabi.coxinhautilities.client.renderer.item;

import me.luligabi.coxinhautilities.client.renderer.item.sink.GrannysSinkItemExtension;
import me.luligabi.coxinhautilities.client.renderer.item.tank.PortableTankItemExtension;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ClientItemExtensionRegistry {


    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
            new PortableTankItemExtension(),
            BlockRegistry.PORTABLE_TANK_MK1_ITEM.get(),
            BlockRegistry.PORTABLE_TANK_MK2_ITEM.get(),
            BlockRegistry.PORTABLE_TANK_MK3_ITEM.get(),
            BlockRegistry.PORTABLE_TANK_MK4_ITEM.get(),
            BlockRegistry.PORTABLE_TANK_MK5_ITEM.get()
        );

        event.registerItem(
            new GrannysSinkItemExtension(),
            BlockRegistry.GRANNYS_SINK_ITEM.get()
        );
    }

}