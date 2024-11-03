package me.luligabi.coxinhautilities.client.renderer.item.tank;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import me.luligabi.coxinhautilities.client.RenderUtil;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlock;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class PortableTankItemRenderer extends BlockEntityWithoutLevelRenderer {

    public PortableTankItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        PortableTankBlock tankBlock = (PortableTankBlock) ((BlockItem) stack.getItem()).getBlock();
        BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(tankBlock.defaultBlockState());

        RenderUtil.drawBakedModelAsItem(
            stack,
            bakedModel,
            ms,
            vcp,
            light,
            overlay
        );


        boolean hasData = stack.get(DataComponents.BLOCK_ENTITY_DATA) != null;
        if(!hasData) return;


        Lighting.setupForFlatItems();

        CompoundTag data = Util.getBlockEntityData(stack);
        FluidStack fluid = Util.getFluidFromNbt(data);

        RenderUtil.drawFluidInTank(
            fluid,
            (float) fluid.getAmount() /tankBlock.getTankTier().getCapacity(),
            ms,
            vcp,
            null,
            null,
            light
        );
        Lighting.setupFor3DItems();
    }

}