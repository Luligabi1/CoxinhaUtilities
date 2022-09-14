package me.luligabi.coxinhautilities.common.block.sponge;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WetLavaSpongeBlock extends Block implements IWittyComment {

    public WetLavaSpongeBlock(Settings settings) {
        super(settings);
    }

    private final BlockState hardenedState = BlockRegistry.LAVA_SPONGE.getDefaultState();


    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = blockView.getBlockState(blockPos);
        return shouldHarden(blockView, blockPos, blockState) ? this.hardenedState : super.getPlacementState(ctx);
    }

    private static boolean shouldHarden(BlockView world, BlockPos pos, BlockState state) {
        return hardensIn(state) || hardensOnAnySide(world, pos);
    }

    private static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
        boolean hardensOnAnySide = false;
        BlockPos.Mutable mutablePos = pos.mutableCopy();

        for(Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(mutablePos);
            if(direction != Direction.DOWN || hardensIn(blockState)) {
                mutablePos.set(pos, direction);
                blockState = world.getBlockState(mutablePos);
                if (hardensIn(blockState) && !blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) {
                    hardensOnAnySide = true;
                    break;
                }
            }
        }
        return hardensOnAnySide;
    }

    private static boolean hardensIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return hardensOnAnySide(world, pos) ? this.hardenedState : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = Direction.random(random);
        if (direction != Direction.UP) {
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            if (!state.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                double x = pos.getX();
                double y = pos.getY();
                double z = pos.getZ();
                if (direction == Direction.DOWN) {
                    y -= 0.05D;
                    x += random.nextDouble();
                    z += random.nextDouble();
                } else {
                    y += random.nextDouble() * 0.8D;
                    if (direction.getAxis() == Direction.Axis.X) {
                        z += random.nextDouble();
                        if (direction == Direction.EAST) {
                            ++x;
                        } else {
                            x += 0.05D;
                        }
                    } else {
                        x += random.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++z;
                        } else {
                            z += 0.05D;
                        }
                    }
                }

                world.addParticle(ParticleTypes.DRIPPING_LAVA, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(Text.translatable("tooltip.coxinhautilities.lava_sponge.witty"));
    }

}