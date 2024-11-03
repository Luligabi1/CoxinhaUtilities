package me.luligabi.coxinhautilities.client.renderer.item.tank;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class PortableTankItemExtension implements IClientItemExtensions {

    private final PortableTankItemRenderer portableTankItemRenderer = new PortableTankItemRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return portableTankItemRenderer;
    }
}