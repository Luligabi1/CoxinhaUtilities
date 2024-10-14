package me.luligabi.coxinhautilities.client.renderer.item;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ClientItemExtensionRegistry {


    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
            new GrannysSinkItemExtension(),
            BlockRegistry.GRANNYS_SINK_ITEM.get()
        );
    }

}