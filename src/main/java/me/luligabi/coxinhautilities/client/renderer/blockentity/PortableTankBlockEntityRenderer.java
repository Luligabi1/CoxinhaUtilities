package me.luligabi.coxinhautilities.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class PortableTankBlockEntityRenderer implements BlockEntityRenderer<PortableTankBlockEntity> {

    public PortableTankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PortableTankBlockEntity entity, float tickDelta, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        FluidStack fluid = entity.fluidStorage.getFluidInTank(0);
        if (fluid.isEmpty()) return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluid);
        if (stillTexture == null) return;

        FluidState state = fluid.getFluid().defaultFluidState();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(state, entity.getLevel(), entity.getBlockPos());
        VertexConsumer builder = vcp.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));


        float fill = (float) entity.fluidStorage.getFluidAmount() / entity.fluidStorage.getCapacity();
        boolean lighterThanAir = fluid.getFluidType().isLighterThanAir();

        float topHeight = !lighterThanAir ? fill : 1 - TANK_W;
        float bottomHeight = !lighterThanAir ? TANK_W : 1 - fill;


        // TOP
        if(entity.fluidStorage.getFluidAmount() < entity.fluidStorage.getCapacity()) {
            ms.pushPose();
            if(lighterThanAir) {
                ms.mulPose(Axis.XP.rotationDegrees(180));
                ms.translate(0f, -1.93 + fill, -1f);
            }
            RenderUtil.drawQuad(builder, ms, 0.16F, topHeight, 0.16F, 0.84F, topHeight, 0.84F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
            ms.popPose();
        }

        // North
        RenderUtil.drawQuad(builder, ms, 0.16F, bottomHeight, 0.16F, 0.84F, topHeight, 0.16F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);

        // South
        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(180));
        ms.translate(-1f, 0, -1.68f);
        RenderUtil.drawQuad(builder, ms, 0.16F, bottomHeight, 0.84F, 0.84F, topHeight, 0.84F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        ms.popPose();

        // West
        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(90));
        ms.translate(-1f, 0, 0);
        RenderUtil.drawQuad(builder, ms, 0.16F, bottomHeight, 0.16F, 0.84F, topHeight, 0.16F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        ms.popPose();

        // East
        ms.pushPose();
        ms.mulPose(Axis.YN.rotationDegrees(90));
        ms.translate(0, 0, -1f);
        RenderUtil.drawQuad(builder, ms, 0.16F, bottomHeight, 0.16F, 0.84F, topHeight, 0.16F, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, tintColor);
        ms.popPose();

    }

    private static final float TANK_W = 0.07F;

}