package me.luligabi.coxinhautilities.client.renderer.item;

import com.mojang.blaze3d.systems.RenderSystem;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import me.luligabi.coxinhautilities.common.util.RenderUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
@Environment(EnvType.CLIENT)
public class PortableTankItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public PortableTankItemRenderer(BlockState tankBlockState) {
        this.tankBlockState = tankBlockState;
        this.tankBlockEntity =  new PortableTankBlockEntity(BlockPos.ORIGIN, tankBlockState);
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack ms, VertexConsumerProvider vcp, int light, int overlay) {
        BakedModel bakedModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(tankBlockState);

        // Render item model itself
        RenderUtil.renderItemWithWrappedModel(MinecraftClient.getInstance().getItemRenderer(),
                bakedModel, tankModel, stack, light, overlay, ms, vcp);

        // Renders fluid using the tank's BER with data from the stack's nbt
        tankBlockEntity.fluidStorage.variant = FluidVariant.blank();
        NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
        if(nbt != null) tankBlockEntity.fromClientTag(nbt);

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