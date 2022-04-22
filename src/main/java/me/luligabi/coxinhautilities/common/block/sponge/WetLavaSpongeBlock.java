package me.luligabi.coxinhautilities.common.block.sponge;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class WetLavaSpongeBlock extends Block implements IWittyComment {

    public WetLavaSpongeBlock(Settings settings) {
        super(settings);
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
    public List<TranslatableText> wittyComments() {
        return List.of(new TranslatableText("tooltip.coxinhautilities.lava_sponge.witty"));
    }

}