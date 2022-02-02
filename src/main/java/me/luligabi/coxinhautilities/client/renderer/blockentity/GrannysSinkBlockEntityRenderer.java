package me.luligabi.coxinhautilities.client.renderer.blockentity;

import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import me.luligabi.coxinhautilities.common.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class GrannysSinkBlockEntityRenderer implements BlockEntityRenderer<GrannysSinkBlockEntity> {

    public GrannysSinkBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(GrannysSinkBlockEntity entity, float tickDelta, MatrixStack ms, VertexConsumerProvider vcp, int light, int overlay) {
        RenderUtil.drawFluidInSink(ms, vcp, entity.getWorld(), entity.getPos());
    }

}