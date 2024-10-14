package me.luligabi.coxinhautilities.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.mixin.HandledScreenAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class WoodenHopperScreen extends AbstractContainerScreen<AbstractContainerMenu> {

    private static final ResourceLocation TEXTURE = CoxinhaUtilities.id("textures/gui/wooden_hopper.png");

    public WoodenHopperScreen(AbstractContainerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title.plainCopy().withStyle(ChatFormatting.WHITE));
        this.imageHeight = 133;

        ((HandledScreenAccessor) this).setPlayerInventoryTitle(playerInventoryTitle.plainCopy().withStyle(ChatFormatting.WHITE));
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics ctx, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        ctx.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        super.render(ctx, mouseX, mouseY, delta);
        renderTooltip(ctx, mouseX, mouseY);
    }
}