package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderOrchidBlock extends CropBlock implements IWittyComment {

    public EnderOrchidBlock() {
        super(FabricBlockSettings.create().noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    }


    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        if(CoxinhaUtilities.CONFIG.hasEnderOrchidStrictPlacement) {
            return floor.isIn(TagRegistry.ENDER_ORCHID_STRICT_PLACEMENT);
        }
        return true;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ItemRegistry.ENDER_ORCHID_SEEDS;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = this.getAge(state);
        if (age >= this.getMaxAge()) return;
        int growthOdds = world.getBlockState(pos.down()).isIn(TagRegistry.ENDER_ORCHID_STRICT_PLACEMENT) ? // Ender Orchids planted on top of non-end stone blocks take longer to grow
                CoxinhaUtilities.CONFIG.enderOrchidRegularGrowthRate : // 100/8 = 12.5%
                CoxinhaUtilities.CONFIG.enderOrchidSpecialGrowthRate; // 100/12 = 8.3%
        if (random.nextInt(growthOdds) == 0) {
            world.setBlockState(pos, this.withAge(age + 1), 2);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override // Doesn't break on any sort of entity collision
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    }


    @Override // Applying bone meal makes the plant go back in age
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = Math.max(getAge(state) - getGrowthAmount(world), 0);
        world.setBlockState(pos, getDefaultState().with(getAgeProperty(), i), 2);
    }

    @Override
    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 2);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(getAgeProperty()) != 0 && state.get(getAgeProperty()) != 7;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(state.get(getAgeProperty()) != 7) return;
        for(int i = 0; i < 2; ++i) {
            int xMultiplier = random.nextInt(2) - 1;
            int zMultiplier = random.nextInt(2) - 1;

            double x = (double) pos.getX() + 0.5 + 0.25 * (double) xMultiplier;
            double y = (float) pos.getY() + random.nextFloat();
            double z = (double) pos.getZ() + 0.5 + 0.25 * (double) zMultiplier;
            double velocityX = random.nextFloat() * (float) random.nextInt(2) * 2 - 1;
            double velocityY = ((double) random.nextFloat() - 0.5) * 0.125;
            double velocityZ = random.nextFloat() * (float) random.nextInt(2) * 2 - 1;
            world.addParticle(ParticleTypes.PORTAL, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.coxinhautilities.ender_orchid.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.ender_orchid.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(
                Text.translatable("tooltip.coxinhautilities.ender_orchid.witty.1"),
                Text.translatable("tooltip.coxinhautilities.ender_orchid.witty.2")
        );
    }


    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[] {
            Block.createCuboidShape(5, 0, 5, 11, 2, 11),
            Block.createCuboidShape(5, 0, 5, 11, 4, 11),
            Block.createCuboidShape(5, 0, 5, 11, 5.5, 11),
            Block.createCuboidShape(5, 0, 5, 11, 6.5, 11),
            Block.createCuboidShape(5, 0, 5, 11, 9, 11),
            Block.createCuboidShape(5, 0, 5, 11, 10.5, 11),
            Block.createCuboidShape(5, 0, 5, 11, 11, 11),
            Block.createCuboidShape(5, 0, 5, 11, 11.5, 11)
    };

}