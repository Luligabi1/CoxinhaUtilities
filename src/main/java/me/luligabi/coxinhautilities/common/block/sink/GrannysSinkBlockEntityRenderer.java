package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.util.RenderUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class GrannysSinkBlockEntityRenderer implements BlockEntityRenderer<GrannysSinkBlockEntity> {

    public GrannysSinkBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(GrannysSinkBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        RenderUtil.drawFluidInSink(entity.getWorld(), entity.getPos(), matrices, vertexConsumers);
    }

}