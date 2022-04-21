package me.luligabi.coxinhautilities.client.screen.trashcan;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FluidTrashCanScreen extends AbstractTrashCanScreen {

    public FluidTrashCanScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    protected Identifier getTextureIdentifier() {
        return new Identifier(CoxinhaUtilities.MOD_ID, "textures/gui/fluid_trash_can.png");
    }

}