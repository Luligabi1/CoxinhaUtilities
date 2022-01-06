package me.luligabi.coxinhautilities.common.util;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

public class Util {

    // Converts Fabric's droplets into Forge's milliBuckets for easier displaying of liquid amounts.
    public static String getMilliBuckets(long dropletAmount) {
        if (dropletAmount == 0L) {
            return "0";
        } else if (dropletAmount < 81) {
            return "< 1";
        } else {
            return "" + dropletAmount / 81;
        }
    }


    /*
     * Renders fluids inside Portable Tanks.
     * This code is originally from the Modern Industrialization mod, copyrighted by Azercoco & Technici4n, licensed under the MIT license.
     *
     * You may see the original code here: https://github.com/AztechMC/Modern-Industrialization/blob/8e1be7d3b607614ded24f60ec5927d97c6649cc9/src/main/java/aztech/modern_industrialization/util/RenderHelper.java#L124
     */
    @SuppressWarnings("UnstableApiUsage")
    public static void drawFluidInTank(World world, BlockPos pos, MatrixStack ms, VertexConsumerProvider vcp, FluidVariant fluid, float fill) {
        VertexConsumer vc = vcp.getBuffer(RenderLayer.getTranslucent());
        Sprite sprite = FluidVariantRendering.getSprite(fluid);
        int color = FluidVariantRendering.getColor(fluid, world, pos);
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
        if (FluidVariantRendering.fillsFromTop(fluid)) {
            topHeight = 1 - TANK_W;
            bottomHeight = 1 - fill;
        }

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        for (Direction direction : Direction.values()) {
            QuadEmitter emitter = renderer.meshBuilder().getEmitter();

            if (direction.getAxis().isVertical()) {
                emitter.square(direction, SQUARE_START, SQUARE_START, SQUARE_FINAL, SQUARE_FINAL, direction == Direction.UP ? 1 - topHeight : bottomHeight);
            } else {
                emitter.square(direction, SQUARE_START, bottomHeight, SQUARE_FINAL, topHeight, SQUARE_START);
            }

            emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.spriteColor(0, -1, -1, -1, -1);
            vc.quad(ms.peek(), emitter.toBakedQuad(0, sprite, false), r, g, b, FULL_LIGHT, OverlayTexture.DEFAULT_UV);
        }
    }

    public static final float TANK_W = 0.01F;
    public static final float SQUARE_START = 0.16F;
    public static final float SQUARE_FINAL = 0.84F;
    public static final int FULL_LIGHT = 0x00F0_00F0;

    public static final DustParticleEffect AQUATIC_TORCH_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0x2f9799)), 1.0F);
}