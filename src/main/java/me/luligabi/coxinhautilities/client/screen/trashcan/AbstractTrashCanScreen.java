package me.luligabi.coxinhautilities.client.screen.trashcan;

import com.mojang.blaze3d.systems.RenderSystem;
import joptsimple.internal.Strings;
import me.luligabi.coxinhautilities.mixin.HandledScreenAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractTrashCanScreen extends HandledScreen<ScreenHandler> {

    public AbstractTrashCanScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title.copyContentOnly().formatted(Formatting.WHITE));
        this.backgroundHeight = 167;

        ((HandledScreenAccessor) this).setPlayerInventoryTitle(playerInventoryTitle.copyContentOnly().formatted(Formatting.WHITE));
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getTextureIdentifier());
        ctx.drawTexture(getTextureIdentifier(), x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        drawMouseoverTooltip(ctx, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext ctx, int mouseX, int mouseY) {
        String titleLine2Spaced = Strings.join(titleLine2, " ");

        ctx.drawText(textRenderer, titleline1, backgroundWidth/2 - textRenderer.getWidth(titleline1)/2, 6, 0xFFFFFF, false);
        ctx.drawText(textRenderer, titleLine2Spaced, backgroundWidth/2 - textRenderer.getWidth(titleLine2Spaced)/2, 17, 0xFFFFFF, false);


        ctx.drawText(textRenderer, playerInventoryTitle, 8, backgroundHeight - 96 + 4, 0xFFFFFF, false);
        //textRenderer.draw(ctx, playerInventoryTitle, 8f, backgroundHeight - 96 + 4f, 0xFFFFFF);
    }

    List<String> titleString = Arrays.asList(title.getString().split(" ").clone());
    String titleline1 = titleString.get(0);
    List<String> titleLine2 = titleString.subList(1, titleString.size());

    protected abstract Identifier getTextureIdentifier();

}