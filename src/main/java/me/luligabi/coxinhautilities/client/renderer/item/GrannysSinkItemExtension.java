package me.luligabi.coxinhautilities.client.renderer.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class GrannysSinkItemExtension implements IClientItemExtensions {

    private final GrannysSinkItemRenderer grannysSinkItemRenderer = new GrannysSinkItemRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return grannysSinkItemRenderer;
    }
}