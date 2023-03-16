package me.luligabi.coxinhautilities.common.block.sink;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrannysSinkBlock extends HorizontalFacingBlock implements BlockEntityProvider, IWittyComment {

    public GrannysSinkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (((GrannysSinkBlockEntity) world.getBlockEntity(pos)).fluidIo(player, hand)) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GrannysSinkBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.coxinhautilities.grannys_sink.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.grannys_sink.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.grannys_sink.3").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.grannys_sink.4").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.grannys_sink.5").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(Text.translatable("tooltip.coxinhautilities.grannys_sink.witty"));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> NORTH_VOXELSHAPE;
            case SOUTH -> SOUTH_VOXELSHAPE;
            case WEST -> WEST_VOXELSHAPE;
            case EAST -> EAST_VOXELSHAPE;
            default -> VoxelShapes.empty();
        };
    }

    private static final VoxelShape NORTH_VOXELSHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.5D, 16.0D, 14.0D, 15.25D);
    private static final VoxelShape SOUTH_VOXELSHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.75D, 16.0D, 14.0D, 15.5D);
    private static final VoxelShape WEST_VOXELSHAPE = Block.createCuboidShape(0.5D, 0.0D, 0.0D, 15.0D, 14.0D, 16.0D);
    private static final VoxelShape EAST_VOXELSHAPE = Block.createCuboidShape(0.75D, 0.0D, 0.0D, 15.5D, 14.0D, 16.0D);

}