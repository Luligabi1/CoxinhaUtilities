package me.luligabi.coxinhautilities.common.block.aquatictorch;

import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AquaticTorchBlock extends TorchBlock implements Waterloggable {

    public AquaticTorchBlock(Settings settings) {
        super(settings, null);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, true));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.7D;
        double z = pos.getZ() + 0.5D;
        world.addParticle(Util.AQUATIC_TORCH_PARTICLE, x, y, z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        FluidState fluidState = world.getBlockState(pos).getFluidState();
        return super.canPlaceAt(state, world, pos) && (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return super.getPlacementState(ctx).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }

    @Override
    public FluidState getFluidState(BlockState state) { return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state); }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
}
