package me.luligabi.coxinhautilities.client;

import me.luligabi.coxinhautilities.client.renderer.blockentity.DryingRackBlockEntityRenderer;
import me.luligabi.coxinhautilities.client.renderer.blockentity.GrannysSinkBlockEntityRenderer;
import me.luligabi.coxinhautilities.client.renderer.blockentity.PortableTankBlockEntityRenderer;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.ModConfig;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = CoxinhaUtilities.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockEntityRendererRegistry {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
            BlockEntityRegistry.PORTABLE_TANK.get(),
            PortableTankBlockEntityRenderer::new
        );

        event.registerBlockEntityRenderer(
            BlockEntityRegistry.GRANNYS_SINK.get(),
            GrannysSinkBlockEntityRenderer::new
        );

        event.registerBlockEntityRenderer(
            BlockEntityRegistry.DRYING_RACK.get(),
            DryingRackBlockEntityRenderer::new
        );
    }

}