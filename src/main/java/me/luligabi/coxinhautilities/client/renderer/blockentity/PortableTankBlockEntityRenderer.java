package me.luligabi.coxinhautilities.client.renderer.blockentity;

import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import me.luligabi.coxinhautilities.client.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@SuppressWarnings("UnstableApiUsage")
@Environment(EnvType.CLIENT)
public class PortableTankBlockEntityRenderer implements BlockEntityRenderer<PortableTankBlockEntity> {

    public PortableTankBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(PortableTankBlockEntity entity, float tickDelta, MatrixStack ms, VertexConsumerProvider vcp, int light, int overlay) {
        if (entity.fluidStorage.isResourceBlank() && entity.fluidStorage.amount <= 0) return;
        RenderUtil.drawFluidInTank(entity.fluidStorage.variant, (float) entity.fluidStorage.amount / entity.fluidStorage.getCapacity(),
                ms, vcp, entity.getWorld(), entity.getPos());
    }

}