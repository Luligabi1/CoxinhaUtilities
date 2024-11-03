package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.client.renderer.item.ClientItemExtensionRegistry;
import me.luligabi.coxinhautilities.client.screen.WoodenHopperScreen;
import me.luligabi.coxinhautilities.client.screen.trashcan.EnergyTrashCanScreen;
import me.luligabi.coxinhautilities.client.screen.trashcan.FluidTrashCanScreen;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.ModConfig;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import me.luligabi.coxinhautilities.common.screenhandler.MenuTypeRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CoxinhaUtilities.MOD_ID, dist = Dist.CLIENT)
public class CoxinhaUtilitiesClient {

    public CoxinhaUtilitiesClient(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onMenuRegister);
        modEventBus.addListener(ClientItemExtensionRegistry::registerClientExtensions);
        modEventBus.addListener(ItemGroupInit::onCreativeModeTabBuild);
    }

    @SubscribeEvent
    public void onClientInit(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory.class,
            () -> (client, parent) -> ModConfig.HANDLER.generateGui().generateScreen(parent)
        );
    }

    @SubscribeEvent
    public void onMenuRegister(RegisterMenuScreensEvent event) {
        event.register(MenuTypeRegistry.WOODEN_HOPPER.get(), WoodenHopperScreen::new);
        event.register(MenuTypeRegistry.FLUID_TRASH_CAN.get(), FluidTrashCanScreen::new);
        event.register(MenuTypeRegistry.ENERGY_TRASH_CAN.get(), EnergyTrashCanScreen::new);
    }
    
}