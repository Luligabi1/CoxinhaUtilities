package me.luligabi.coxinhautilities.common.block.sponge;

import com.google.common.collect.Lists;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.Queue;

public class LavaSpongeBlock extends SpongeBlock implements IWittyComment {

    public LavaSpongeBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE).mapColor(MapColor.FIRE));
    }

    @Override
    protected void tryAbsorbWater(Level world, BlockPos pos) {
        if(!canAbsorb(world, pos)) return;
        world.setBlock(pos, BlockRegistry.WET_LAVA_SPONGE.get().defaultBlockState(), 2);
        world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(Blocks.LAVA.defaultBlockState()));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.lava_sponge.witty"));
    }

    private boolean canAbsorb(Level world, BlockPos pos) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple<>(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> pair = queue.poll();
            BlockPos blockPos = pair.getA();
            int j = pair.getB();
            Direction[] var8 = Direction.values();

            for (Direction direction : var8) {
                BlockPos blockPos2 = blockPos.relative(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                FluidState fluidState = world.getFluidState(blockPos2);
                if (fluidState.is(FluidTags.LAVA)) {
                    if (blockState.getBlock() instanceof BucketPickup && !((BucketPickup) blockState.getBlock()).pickupBlock(null, world, blockPos2, blockState).isEmpty()) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockPos2, j + 1));
                        }
                    } else if (blockState.getBlock() instanceof LiquidBlock) {
                        world.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }

}