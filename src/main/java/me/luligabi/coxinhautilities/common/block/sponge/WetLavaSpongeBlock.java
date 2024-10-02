package me.luligabi.coxinhautilities.common.block.sponge;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public class WetLavaSpongeBlock extends Block implements IWittyComment {


    public WetLavaSpongeBlock() {
        super(FabricBlockSettings.copyOf(Blocks.WET_SPONGE).mapColor(MapColor.NETHER));
    }

    private final BlockState hardenedState = BlockRegistry.LAVA_SPONGE.defaultBlockState();


    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockGetter blockView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        BlockState blockState = blockView.getBlockState(blockPos);
        return shouldHarden(blockView, blockPos, blockState) ? this.hardenedState : super.getStateForPlacement(ctx);
    }

    private static boolean shouldHarden(BlockGetter world, BlockPos pos, BlockState state) {
        return hardensIn(state) || hardensOnAnySide(world, pos);
    }

    private static boolean hardensOnAnySide(BlockGetter world, BlockPos pos) {
        boolean shouldHarden = false;
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for(Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(mutable);
            if(direction != Direction.DOWN || hardensIn(blockState)) {
                mutable.setWithOffset(pos, direction);
                blockState = world.getBlockState(mutable);
                if(hardensIn(blockState) && !blockState.isFaceSturdy(world, pos, direction.getOpposite())) {
                    shouldHarden = true;
                    break;
                }
            }
        }

        return shouldHarden;
    }

    private static boolean hardensIn(BlockState state) {
        return state.getFluidState().is(FluidTags.WATER);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return hardensOnAnySide(world, pos) ? this.hardenedState : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }


    @Override
    public void handlePrecipitation(BlockState state, Level world, BlockPos pos, Biome.Precipitation precipitation) {
        if(precipitation == Biome.Precipitation.RAIN && world.getRandom().nextFloat() < 0.35F) {
            world.setBlockAndUpdate(pos, BlockRegistry.LAVA_SPONGE.defaultBlockState());
            world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if(direction != Direction.UP) {
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = world.getBlockState(blockPos);
            if(!state.canOcclude() || !blockState.isFaceSturdy(world, blockPos, direction.getOpposite())) {
                double x = pos.getX();
                double y = pos.getY();
                double z = pos.getZ();
                if(direction == Direction.DOWN) {
                    y -= 0.05D;
                    x += random.nextDouble();
                    z += random.nextDouble();
                } else {
                    y += random.nextDouble() * 0.8D;
                    if(direction.getAxis() == Direction.Axis.X) {
                        z += random.nextDouble();
                        if(direction == Direction.EAST) {
                            ++x;
                        } else {
                            x += 0.05D;
                        }
                    } else {
                        x += random.nextDouble();
                        if(direction == Direction.SOUTH) {
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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.lava_sponge.witty"));
    }

}