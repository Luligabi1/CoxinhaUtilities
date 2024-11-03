package me.luligabi.coxinhautilities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class RenderUtil {

    public static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.addVertex(poseStack.last().pose(), x, y, z)
            .setColor(color)
            .setUv(u, v)
            .setLight(packedLight)
            .setNormal(1, 0, 0);
    }

    public static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
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
    public static void drawFluidInSink(PoseStack ms, MultiBufferSource vcp, @Nullable Level world, @Nullable BlockPos pos) {
        /*FluidVariant water = FluidVariant.of(Fluids.WATER);
        VertexConsumer vc = vcp.getBuffer(RenderType.cutout());
        TextureAtlasSprite sprite = FluidVariantRendering.getSprite(water);
        int color = (world == null && pos == null) ? FluidVariantRendering.getColor(water, null, null) : FluidVariantRendering.getColor(water, world, pos);
        float r = ((color >> 16) & 255) / 256f;
        float g = ((color >> 8) & 255) / 256f;
        float b = (color & 255) / 256f;

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        QuadEmitter emitter = renderer.meshBuilder().getEmitter();

        emitter.square(Direction.UP, 0.16F, 0.18F, 0.84F, 0.82F, 0.18F);
        emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
        emitter.spriteColor(0, -1, -1, -1, -1);
        vc.putBulkData(ms.last(), emitter.toBakedQuad(sprite), r, g, b, 1, FULL_LIGHT, OverlayTexture.NO_OVERLAY);*/
    }

    public static final int FULL_LIGHT = 0x00F0_00F0;

    /*public static void renderItemWithWrappedModel(ItemRenderer renderer, BakedModel model, WrappedBakedModel wrappedModel, ItemStack stack, int light, int overlay, PoseStack ms, MultiBufferSource vcp) {
        wrappedModel.setWrappedModel(model);
        ms.pushPose();
        ms.translate(0.5D, 0.5D, 0.5D);
        renderer.render(stack, ItemDisplayContext.NONE, false, ms, vcp, light, overlay, wrappedModel);
        ms.popPose();
    }*/


    /*
     * A special implementation of a Baked Model, wrapped inside another Baked Model.
     *
     * Yes, it is very cursed, but it's the ~~lazier~~ easier way to do fluid
     * rendering on item models while touching as little rendering code as possible.
     */
    // FIXME
    /*public static class WrappedBakedModel extends ForwardingBakedModel {

        public void setWrappedModel(BakedModel wrappedModel) { this.wrapped = wrappedModel; }

    }*/

}