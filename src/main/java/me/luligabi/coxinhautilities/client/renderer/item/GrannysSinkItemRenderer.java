package me.luligabi.coxinhautilities.client.renderer.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class GrannysSinkItemRenderer extends BlockEntityWithoutLevelRenderer {

    public GrannysSinkItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack ps, MultiBufferSource bufferSource, int light, int overlay) {
        BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(sinkBlockState);

        // Render item model itself FIXME
        /*RenderUtil.renderItemWithWrappedModel(Minecraft.getInstance().getItemRenderer(),
            bakedModel, sinkModel, stack, light, overlay, ps, bufferSource);*/

        // Renders fluid using the sink's BER
        Lighting.setupForFlatItems();
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(
            sinkBlockEntity, ps, bufferSource, light, overlay);
        Lighting.setupFor3DItems();
    }

    private final BlockState sinkBlockState = BlockRegistry.GRANNYS_SINK.get().defaultBlockState();
    private final GrannysSinkBlockEntity sinkBlockEntity = new GrannysSinkBlockEntity(BlockPos.ZERO, sinkBlockState);
    //private final RenderUtil.WrappedBakedModel sinkModel = new RenderUtil.WrappedBakedModel();

}