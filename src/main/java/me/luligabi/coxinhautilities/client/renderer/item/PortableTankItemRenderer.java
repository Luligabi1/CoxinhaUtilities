package me.luligabi.coxinhautilities.client.renderer.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class PortableTankItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    public PortableTankItemRenderer(BlockState tankBlockState) {
        this.tankBlockState = tankBlockState;
        this.tankBlockEntity =  new PortableTankBlockEntity(BlockPos.ZERO, tankBlockState);
    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(tankBlockState);

        // Render item model itself
        RenderUtil.renderItemWithWrappedModel(Minecraft.getInstance().getItemRenderer(),
                bakedModel, tankModel, stack, light, overlay, ms, vcp);

        // Renders fluid using the tank's BER with data from the stack's nbt
        tankBlockEntity.fluidStorage.variant = FluidVariant.blank();
        CustomData nbt = stack.get(DataComponents.BLOCK_ENTITY_DATA); // FIXME might be getting incorrect value?
        if(nbt != null) tankBlockEntity.fromClientTag(nbt.copyTag(), Minecraft.getInstance().level.registryAccess());

        Lighting.setupForFlatItems();
        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(
                tankBlockEntity, ms, vcp, light, overlay);
        Lighting.setupFor3DItems();
        RenderSystem.enableDepthTest();
    }

    private final BlockState tankBlockState;
    private final PortableTankBlockEntity tankBlockEntity;
    private final RenderUtil.WrappedBakedModel tankModel = new RenderUtil.WrappedBakedModel();

}