package me.luligabi.coxinhautilities.client.renderer.blockentity;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

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
            ModelTransformation transformation = MinecraftClient.getInstance().getItemRenderer().getModel(entity.getStack(), null, null, 0).getTransformation();
            boolean isBlock = transformation.fixed.equals(new Transformation(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0.5F, 0.5F, 0.5F)));

            setItemPosition(ms, direction, isBlock);

            RotationAxis axis;
            if(isBlock) {
                axis = direction.getAxis() == Direction.Axis.Z ? RotationAxis.POSITIVE_X : RotationAxis.POSITIVE_Y;
            } else {
                axis = direction.getAxis() == Direction.Axis.Z ? RotationAxis.NEGATIVE_X : RotationAxis.NEGATIVE_Y;
            }
            ms.multiply(axis.rotationDegrees(getItemAngle(direction)));

            // gambiarra: fix south blocks and north items being mirrored and/or upside down
            if((direction == Direction.SOUTH && isBlock) || (direction == Direction.NORTH && !isBlock)) {
                ms.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                ms.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            }

            float scale = isBlock ? 0.75F : 0.99F;
            ms.scale(scale, scale, scale);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(), isBlock ? ModelTransformationMode.NONE : ModelTransformationMode.GUI, light, OverlayTexture.DEFAULT_UV, ms, vcp, entity.getWorld(), (int) entity.getPos().asLong());
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
            case SOUTH -> 0 * 90;
            case WEST, EAST -> direction.getHorizontal() * 90;
            default -> throw new IllegalStateException("Unexpected Drying Rack direction: " + direction);
        };
    }

    private void setItemPosition(MatrixStack ms, Direction direction, boolean isBlock) {
        double center = 0.5D;
        double offset;
        if(direction.getDirection() == Direction.AxisDirection.NEGATIVE) { // North, West
            offset = isBlock ? 0.635 : 0.97;
        } else { // South, East
            offset = isBlock ? 0.365 : 0.03;
        }

        switch(direction) {
            case NORTH, SOUTH -> ms.translate(center, center, offset);
            case WEST, EAST -> ms.translate(offset, center, center);
            default -> throw new IllegalStateException("Unexpected Drying Rack direction: " + direction);
        }
    }

}