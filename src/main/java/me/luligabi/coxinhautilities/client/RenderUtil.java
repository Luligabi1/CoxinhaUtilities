package me.luligabi.coxinhautilities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
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

    
    public static void drawFluidInTank(FluidStack fluid, float fill, PoseStack ms, MultiBufferSource vcp, @Nullable Level world, @Nullable BlockPos pos, int light) {
        if (fluid.isEmpty()) return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluid);
        if (stillTexture == null) return;

        FluidState state = fluid.getFluid().defaultFluidState();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = (world == null && pos == null) ? fluidTypeExtensions.getTintColor(fluid) : fluidTypeExtensions.getTintColor(state, world, pos);
        VertexConsumer builder = vcp.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        boolean lighterThanAir = fluid.getFluidType().isLighterThanAir();
        float topHeight = !lighterThanAir ? fill : 1 - TANK_W;
        float bottomHeight = !lighterThanAir ? TANK_W : 1 - fill;

        // TOP
        if(fill < 1f) {
            ms.pushPose();
            if(lighterThanAir) {
                ms.mulPose(Axis.XP.rotationDegrees(180));
                ms.translate(0f, -1.93 + fill, -1f);
            }
            RenderUtil.drawQuad(builder, ms, TANK_START, topHeight, TANK_START, TANK_FINAL, topHeight, TANK_FINAL, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
            ms.popPose();
        }

        // North
        RenderUtil.drawQuad(builder, ms, TANK_START, bottomHeight, TANK_START, TANK_FINAL, topHeight, TANK_START, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);

        // South
        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(180));
        ms.translate(-1f, 0, -1.68f);
        RenderUtil.drawQuad(builder, ms, TANK_START, bottomHeight, TANK_FINAL, TANK_FINAL, topHeight, TANK_FINAL, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        ms.popPose();

        // West
        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(90));
        ms.translate(-1f, 0, 0);
        RenderUtil.drawQuad(builder, ms, TANK_START, bottomHeight, TANK_START, TANK_FINAL, topHeight, TANK_START, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        ms.popPose();

        // East
        ms.pushPose();
        ms.mulPose(Axis.YN.rotationDegrees(90));
        ms.translate(0, 0, -1f);
        RenderUtil.drawQuad(builder, ms, TANK_START, bottomHeight, TANK_START, TANK_FINAL, topHeight, TANK_START, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        ms.popPose();
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
    public static void drawWaterInSink(PoseStack ms, MultiBufferSource vcp, @Nullable Level world, @Nullable BlockPos pos, int light) {
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(WATER.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(WATER);
        if (stillTexture == null) return;

        FluidState state = WATER.getFluid().defaultFluidState();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = (world == null && pos == null) ? fluidTypeExtensions.getTintColor(WATER) : fluidTypeExtensions.getTintColor(state, world, pos);
        VertexConsumer builder = vcp.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

        RenderUtil.drawQuad(builder, ms, TANK_START, 0.82f, TANK_START, TANK_FINAL, 0.82f, TANK_FINAL, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
    }

    private static final FluidStack WATER = new FluidStack(Fluids.WATER, 1);

    public static void drawBakedModelAsItem(ItemStack stack, BakedModel model, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        ms.pushPose();
        ms.translate(0.5D, 0.5D, 0.5D);
        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, ms, vcp, light, overlay, model);
        ms.popPose();
    }

}