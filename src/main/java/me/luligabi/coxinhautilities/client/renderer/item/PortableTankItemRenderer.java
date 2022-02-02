package me.luligabi.coxinhautilities.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
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
public class PortableTankItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public PortableTankItemRenderer(BlockState tankBlockState) {
        this.tankBlockState = tankBlockState;
        this.tankBlockEntity =  new PortableTankBlockEntity(BlockPos.ORIGIN, tankBlockState);
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack ms, VertexConsumerProvider vcp, int light, int overlay) {
        BakedModel bakedModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(tankBlockState);

        RenderUtil.renderItemWithWrappedModel(MinecraftClient.getInstance().getItemRenderer(),
                bakedModel, tankModel, stack, light, overlay, ms, vcp);

        DiffuseLighting.disableGuiDepthLighting();
        MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(
                tankBlockEntity, ms, vcp, light, overlay);
        DiffuseLighting.enableGuiDepthLighting();
        RenderSystem.enableDepthTest();
    }

    private final BlockState tankBlockState;
    private final PortableTankBlockEntity tankBlockEntity;
    private final RenderUtil.WrappedBakedModel tankModel = new RenderUtil.WrappedBakedModel();

}