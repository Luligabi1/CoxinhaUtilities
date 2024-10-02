package me.luligabi.coxinhautilities.client.renderer.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.sink.GrannysSinkBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class GrannysSinkItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(sinkBlockState);

        // Render item model itself
        RenderUtil.renderItemWithWrappedModel(Minecraft.getInstance().getItemRenderer(),
                bakedModel, sinkModel, stack, light, overlay, ms, vcp);

        // Renders fluid using the sink's BER
        Lighting.setupForFlatItems();
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(
                sinkBlockEntity, ms, vcp, light, overlay);
        Lighting.setupFor3DItems();
    }

    private final BlockState sinkBlockState = BlockRegistry.GRANNYS_SINK.defaultBlockState();
    private final GrannysSinkBlockEntity sinkBlockEntity = new GrannysSinkBlockEntity(BlockPos.ZERO, sinkBlockState);
    private final RenderUtil.WrappedBakedModel sinkModel = new RenderUtil.WrappedBakedModel();

}