package me.luligabi.coxinhautilities.common.util;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RenderUtil {

    /*
     * Renders fluids inside Portable Tanks.
     * This code is derivative of the one found in Modern Industrialization, copyrighted by Azercoco & Technici4n and licensed under MIT.
     *
     * You may see the original code here: https://github.com/AztechMC/Modern-Industrialization/blob/8e1be7d3b607614ded24f60ec5927d97c6649cc9/src/main/java/aztech/modern_industrialization/util/RenderHelper.java#L124
     */
    @SuppressWarnings("UnstableApiUsage")
    public static void drawFluidInTank(FluidVariant fluid, float fill, MatrixStack ms, VertexConsumerProvider vcp, @Nullable World world, @Nullable BlockPos pos) {
        VertexConsumer vc = vcp.getBuffer(RenderLayer.getCutout());
        Sprite sprite = FluidVariantRendering.getSprite(fluid);
        if(sprite == null) return;

        int color = (world == null && pos == null) ? FluidVariantRendering.getColor(fluid, null, null) : FluidVariantRendering.getColor(fluid, world, pos);
        float r = ((color >> 16) & 255) / 256f;
        float g = ((color >> 8) & 255) / 256f;
        float b = (color & 255) / 256f;

        // Make sure fill is within [TANK_W, 1 - TANK_W]
        fill = Math.min(fill, 1 - TANK_W);
        fill = Math.max(fill, TANK_W);
        // Top and bottom positions of the fluid inside the tank
        float topHeight = fill;
        float bottomHeight = TANK_W;
        // Render gas from top to bottom
        if (FluidVariantAttributes.isLighterThanAir(fluid)) {
            topHeight = 1 - TANK_W;
            bottomHeight = 1 - fill;
        }

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        for (Direction direction : Direction.values()) {
            QuadEmitter emitter = renderer.meshBuilder().getEmitter();

            if (direction.getAxis().isVertical()) {
                emitter.square(direction, TANK_START, TANK_START, TANK_FINAL, TANK_FINAL, direction == Direction.UP ? 1 - topHeight : bottomHeight);
            } else {
                emitter.square(direction, TANK_START, bottomHeight, TANK_FINAL, topHeight, TANK_START);
            }

            emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.spriteColor(0, -1, -1, -1, -1);
            vc.quad(ms.peek(), emitter.toBakedQuad(0, sprite, false), r, g, b, FULL_LIGHT, OverlayTexture.DEFAULT_UV);
        }
    }

    public static final float TANK_W = 0.07F;
    public static final float TANK_START = 0.16F;
    public static final float TANK_FINAL = 0.84F;

    /*
     * Renders the Water inside Granny's Sink.
     * This code is derivative of the one found in Modern Industrialization, copyrighted by Azercoco & Technici4n and licensed under MIT.
     *
     * You may see the original code here: https://github.com/AztechMC/Modern-Industrialization/blob/8e1be7d3b607614ded24f60ec5927d97c6649cc9/src/main/java/aztech/modern_industrialization/util/RenderHelper.java#L124
     */
    @SuppressWarnings("UnstableApiUsage")
    public static void drawFluidInSink(MatrixStack ms, VertexConsumerProvider vcp, @Nullable World world, @Nullable BlockPos pos) {
        FluidVariant water = FluidVariant.of(Fluids.WATER);
        VertexConsumer vc = vcp.getBuffer(RenderLayer.getCutout());
        Sprite sprite = FluidVariantRendering.getSprite(water);
        int color = (world == null && pos == null) ? FluidVariantRendering.getColor(water, null, null) : FluidVariantRendering.getColor(water, world, pos);
        float r = ((color >> 16) & 255) / 256f;
        float g = ((color >> 8) & 255) / 256f;
        float b = (color & 255) / 256f;

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        QuadEmitter emitter = renderer.meshBuilder().getEmitter();

        emitter.square(Direction.UP, 0.16F, 0.18F, 0.84F, 0.82F, 0.18F);
        emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
        emitter.spriteColor(0, -1, -1, -1, -1);
        vc.quad(ms.peek(), emitter.toBakedQuad(0, sprite, false), r, g, b, FULL_LIGHT, OverlayTexture.DEFAULT_UV);
    }

    public static final int FULL_LIGHT = 0x00F0_00F0;

    public static void renderItemWithWrappedModel(ItemRenderer renderer, BakedModel model, WrappedBakedModel wrappedModel, ItemStack stack, int light, int overlay, MatrixStack ms, VertexConsumerProvider vcp) {
        wrappedModel.setWrappedModel(model);
        ms.push();
        ms.translate(0.5D, 0.5D, 0.5D);
        renderer.renderItem(stack, ModelTransformation.Mode.NONE, false, ms, vcp, light, overlay, wrappedModel);
        ms.pop();
    }


    /*
     * A special implementation of a Baked Model, wrapped inside another Baked Model.
     *
     * Yes, it is very cursed, but it's the ~~lazier~~ easier way to do fluid
     * rendering on item models while touching as little rendering code as possible.
     */
    public static class WrappedBakedModel extends ForwardingBakedModel {

        public void setWrappedModel(BakedModel wrappedModel) { this.wrapped = wrappedModel; }

    }

}