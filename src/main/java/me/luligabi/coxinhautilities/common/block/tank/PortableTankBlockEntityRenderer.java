package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlockEntityRenderer implements BlockEntityRenderer<PortableTankBlockEntity> {

    public PortableTankBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(PortableTankBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.fluidStorage.getResource().isBlank() && entity.fluidStorage.amount > 0) {
            Util.drawFluidInTank(entity.getWorld(), entity.getPos(), matrices, vertexConsumers,
                    entity.fluidStorage.variant, (float) entity.fluidStorage.amount / entity.fluidStorage.getCapacity());
        }
    }
}