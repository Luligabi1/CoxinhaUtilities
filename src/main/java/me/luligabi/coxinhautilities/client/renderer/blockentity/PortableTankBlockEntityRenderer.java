package me.luligabi.coxinhautilities.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class PortableTankBlockEntityRenderer implements BlockEntityRenderer<PortableTankBlockEntity> {

    public PortableTankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PortableTankBlockEntity entity, float tickDelta, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        if (entity.fluidStorage.isEmpty()) return;
        RenderUtil.drawFluidInTank(entity.fluidStorage.getFluidInTank(0), (float) entity.fluidStorage.getFluidAmount() / entity.fluidStorage.getCapacity(),
                ms, vcp, entity.getLevel(), entity.getBlockPos());
    }

}