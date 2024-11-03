package me.luligabi.coxinhautilities.client.renderer.item.sink;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class GrannysSinkItemRenderer extends BlockEntityWithoutLevelRenderer {

    public GrannysSinkItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(sinkBlockState);

        RenderUtil.drawBakedModelAsItem(
            stack,
            bakedModel,
            ms,
            vcp,
            light,
            overlay
        );

        Lighting.setupForFlatItems();
        RenderUtil.drawWaterInSink(ms, vcp, null, null, light);
        Lighting.setupFor3DItems();
    }

    private final BlockState sinkBlockState = BlockRegistry.GRANNYS_SINK.get().defaultBlockState();

}