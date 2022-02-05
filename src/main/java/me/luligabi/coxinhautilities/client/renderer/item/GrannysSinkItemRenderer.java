package me.luligabi.coxinhautilities.client.renderer.item;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import me.luligabi.coxinhautilities.common.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class GrannysSinkItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack ms, VertexConsumerProvider vcp, int light, int overlay) {
        BakedModel bakedModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(sinkBlockState);

        // Render item model itself
        RenderUtil.renderItemWithWrappedModel(MinecraftClient.getInstance().getItemRenderer(),
                bakedModel, sinkModel, stack, light, overlay, ms, vcp);

        // Renders fluid using the sink's BER
        DiffuseLighting.disableGuiDepthLighting();
        MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(
                sinkBlockEntity, ms, vcp, light, overlay);
        DiffuseLighting.enableGuiDepthLighting();
    }

    private final BlockState sinkBlockState = BlockRegistry.GRANNYS_SINK.getDefaultState();
    private final GrannysSinkBlockEntity sinkBlockEntity = new GrannysSinkBlockEntity(BlockPos.ORIGIN, sinkBlockState);
    private final RenderUtil.WrappedBakedModel sinkModel = new RenderUtil.WrappedBakedModel();

}