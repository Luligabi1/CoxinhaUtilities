package me.luligabi.coxinhautilities.common.block.aquatictorch;

import com.mojang.serialization.MapCodec;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class WallAquaticTorchBlock extends AquaticTorchBlock implements SimpleWaterloggedBlock {

    public WallAquaticTorchBlock(Properties settings) {
        super(settings);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, true));
    }

    @Override
    public MapCodec<WallAquaticTorchBlock> codec() {
        return simpleCodec(WallAquaticTorchBlock::new);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.7D;
        double z = pos.getZ() + 0.5D;
        Direction directionOpposite = direction.getOpposite();
        world.addParticle(Util.AQUATIC_TORCH_PARTICLE, x + 0.27D * directionOpposite.getStepX(), y + 0.22D, z + 0.27D * directionOpposite.getStepZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        FluidState fluidState = world.getBlockState(pos).getFluidState();
        return super.canSurvive(state, world, pos) && (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockstate = defaultBlockState();
        BlockPos blockpos = ctx.getClickedPos();
        Direction[] directionArray = ctx.getNearestLookingDirections();

        for(Direction direction : directionArray) {
            if (direction.getAxis().isHorizontal()) {
                Direction directionOpposite = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, directionOpposite);
                if (blockstate.canSurvive(ctx.getLevel(), blockpos)) break;
            }
        }

        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return blockstate.setValue(WATERLOGGED, (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8));
    }

    @Override
    public FluidState getFluidState(BlockState state) { return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
}