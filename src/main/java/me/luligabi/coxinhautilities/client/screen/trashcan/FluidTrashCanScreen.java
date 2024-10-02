package me.luligabi.coxinhautilities.client.screen.trashcan;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class FluidTrashCanScreen extends AbstractTrashCanScreen {

    public FluidTrashCanScreen(AbstractContainerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }


    @Override
    protected ResourceLocation getTextureIdentifier() {
        return CoxinhaUtilities.id("textures/gui/fluid_trash_can.png");
    }

}