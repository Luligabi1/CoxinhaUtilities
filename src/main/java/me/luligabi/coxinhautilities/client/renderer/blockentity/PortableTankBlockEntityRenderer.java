package me.luligabi.coxinhautilities.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@SuppressWarnings("UnstableApiUsage")
@Environment(EnvType.CLIENT)
public class PortableTankBlockEntityRenderer implements BlockEntityRenderer<PortableTankBlockEntity> {

    public PortableTankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PortableTankBlockEntity entity, float tickDelta, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        if (entity.fluidStorage.isResourceBlank() && entity.fluidStorage.amount <= 0) return;
        RenderUtil.drawFluidInTank(entity.fluidStorage.variant, (float) entity.fluidStorage.amount / entity.fluidStorage.getCapacity(),
                ms, vcp, entity.getLevel(), entity.getBlockPos());
    }

}