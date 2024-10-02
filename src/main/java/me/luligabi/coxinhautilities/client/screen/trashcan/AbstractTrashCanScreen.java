package me.luligabi.coxinhautilities.client.screen.trashcan;

import com.mojang.blaze3d.systems.RenderSystem;
import joptsimple.internal.Strings;
import me.luligabi.coxinhautilities.mixin.HandledScreenAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractTrashCanScreen extends AbstractContainerScreen<AbstractContainerMenu> {

    public AbstractTrashCanScreen(AbstractContainerMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title.plainCopy().withStyle(ChatFormatting.WHITE));
        this.imageHeight = 167;

        ((HandledScreenAccessor) this).setPlayerInventoryTitle(playerInventoryTitle.plainCopy().withStyle(ChatFormatting.WHITE));
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics ctx, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getTextureIdentifier());
        ctx.blit(getTextureIdentifier(), leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        super.render(ctx, mouseX, mouseY, delta);
        renderTooltip(ctx, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics ctx, int mouseX, int mouseY) {
        String titleLine2Spaced = Strings.join(titleLine2, " ");

        ctx.drawString(font, titleline1, imageWidth/2 - font.width(titleline1)/2, 6, 0xFFFFFF, false);
        ctx.drawString(font, titleLine2Spaced, imageWidth/2 - font.width(titleLine2Spaced)/2, 17, 0xFFFFFF, false);


        ctx.drawString(font, playerInventoryTitle, 8, imageHeight - 96 + 4, 0xFFFFFF, false);
        //textRenderer.draw(ctx, playerInventoryTitle, 8f, backgroundHeight - 96 + 4f, 0xFFFFFF);
    }

    List<String> titleString = Arrays.asList(title.getString().split(" ").clone());
    String titleline1 = titleString.get(0);
    List<String> titleLine2 = titleString.subList(1, titleString.size());

    protected abstract ResourceLocation getTextureIdentifier();

}