package me.luligabi.coxinhautilities.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Environment(EnvType.CLIENT)
public class GrannysSinkBlockEntityRenderer implements BlockEntityRenderer<GrannysSinkBlockEntity> {

    public GrannysSinkBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(GrannysSinkBlockEntity entity, float tickDelta, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        RenderUtil.drawFluidInSink(ms, vcp, entity.getLevel(), entity.getBlockPos());
    }

}