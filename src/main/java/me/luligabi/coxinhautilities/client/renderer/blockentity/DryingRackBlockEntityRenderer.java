package me.luligabi.coxinhautilities.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.block.dryingrack.DryingRackBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;

public class DryingRackBlockEntityRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {

    public DryingRackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    /*
     * Renders the item being dried on the drying rack.
     *
     * Code is loosely based on the code seen on ItemFrameEntityRenderer.
     */
    @Override
    public void render(DryingRackBlockEntity entity, float tickDelta, PoseStack ms, MultiBufferSource vcp, int light, int overlay) {
        if(entity.getStack().isEmpty()) return;

        ms.pushPose();
        //noinspection ConstantConditions
        if(entity.getLevel().getBlockState(entity.getBlockPos()).is(BlockRegistry.DRYING_RACK)) {
            Direction direction = entity.getLevel().getBlockState(entity.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING);
            ItemTransforms transformation = Minecraft.getInstance().getItemRenderer().getModel(entity.getStack(), null, null, 0).getTransforms();
            boolean isBlock = transformation.fixed.equals(new ItemTransform(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0.5F, 0.5F, 0.5F)));

            setItemPosition(ms, direction, isBlock);

            Axis axis;
            if(isBlock) {
                axis = direction.getAxis() == Direction.Axis.Z ? Axis.XP : Axis.YP;
            } else {
                axis = direction.getAxis() == Direction.Axis.Z ? Axis.XN : Axis.YN;
            }
            ms.mulPose(axis.rotationDegrees(getItemAngle(direction)));

            // gambiarra: fix south blocks and north items being mirrored and/or upside down
            if((direction == Direction.SOUTH && isBlock) || (direction == Direction.NORTH && !isBlock)) {
                ms.mulPose(Axis.XP.rotationDegrees(180));
                ms.mulPose(Axis.ZP.rotationDegrees(180));
            }

            float scale = isBlock ? 0.75F : 0.99F;
            ms.scale(scale, scale, scale);

            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getStack(), isBlock ? ItemDisplayContext.NONE : ItemDisplayContext.GUI, light, OverlayTexture.NO_OVERLAY, ms, vcp, entity.getLevel(), (int) entity.getBlockPos().asLong());
        }
        ms.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(DryingRackBlockEntity blockEntity) {
        return true;
    }

    private float getItemAngle(Direction direction) {
        return switch(direction) {
            case NORTH -> 0;
            case SOUTH -> 0 * 90;
            case WEST, EAST -> direction.get2DDataValue() * 90;
            default -> throw new IllegalStateException("Unexpected Drying Rack direction: " + direction);
        };
    }

    private void setItemPosition(PoseStack ms, Direction direction, boolean isBlock) {
        double center = 0.5D;
        double offset;
        if(direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE) { // North, West
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