package me.luligabi.coxinhautilities.client.renderer.blockentity;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class DryingRackBlockEntityRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {

    public DryingRackBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    /*
     * Renders the item being dried on the drying rack.
     *
     * Code is loosely based on the code seen on ItemFrameEntityRenderer.
     */
    @Override
    public void render(DryingRackBlockEntity entity, float tickDelta, MatrixStack ms, VertexConsumerProvider vcp, int light, int overlay) {
        if(entity.getStack().isEmpty()) return;

        ms.push();
        //noinspection ConstantConditions
        if(entity.getWorld().getBlockState(entity.getPos()).isOf(BlockRegistry.DRYING_RACK)) {
            Direction direction = entity.getWorld().getBlockState(entity.getPos()).get(Properties.HORIZONTAL_FACING);
            setItemPosition(ms, direction);
            ms.multiply(RotationAxis.POSITIVE_X.rotationDegrees(getItemAngle(direction)));
            ms.scale(0.99F, 0.99F, 0.99F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(), ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, ms, vcp, entity.getWorld(), (int) entity.getPos().asLong());
        }
        ms.pop();
    }

    @Override
    public boolean rendersOutsideBoundingBox(DryingRackBlockEntity blockEntity) {
        return true;
    }


    private float getItemAngle(Direction direction) {
        return switch(direction) {
            case NORTH -> 0;
            case SOUTH -> 2 * 90;
            case WEST, EAST -> direction.getHorizontal() * 90;
            default -> throw new IllegalStateException("Unexpected Drying Rack direction: " + direction);
        };
    }

    private void setItemPosition(MatrixStack ms, Direction direction) {
        switch(direction) {
            case NORTH -> ms.translate(0.5D, 0.5D, 0.97D);
            case SOUTH -> ms.translate(0.5D, 0.5D, 0.03D);
            case WEST -> ms.translate(0.97D, 0.5D, 0.5D);
            case EAST -> ms.translate(0.03D, 0.5D, 0.5D);
            default -> throw new IllegalStateException("Unexpected Drying Rack direction: " + direction);
        }
    }

}