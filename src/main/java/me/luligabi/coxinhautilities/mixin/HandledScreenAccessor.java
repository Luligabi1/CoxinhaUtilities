package me.luligabi.coxinhautilities.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface HandledScreenAccessor {

    @Mutable
    @Accessor("playerInventoryTitle")
    void setPlayerInventoryTitle(Component playerInventoryTitle);
}