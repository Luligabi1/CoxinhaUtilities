package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.ModConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = CoxinhaUtilities.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ConfigScreenRegistry {

    @SubscribeEvent
    public static void onPostInit(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory.class,
            () -> (client, parent) -> ModConfig.HANDLER.generateGui().generateScreen(parent)
        );
    }
}