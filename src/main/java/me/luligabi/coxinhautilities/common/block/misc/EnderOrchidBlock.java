package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.ModConfig;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
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
        super(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    }


    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.END_STONE) || !ModConfig.enderOrchidStrictPlacement;
    }

    protected ItemConvertible getSeedsItem() {
        return ItemRegistry.ENDER_ORCHID_SEEDS;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = this.getAge(state);
        if (age >= this.getMaxAge()) return;
        int growthOdds = world.getBlockState(pos.down()).isOf(Blocks.END_STONE) ? // Ender Orchids planted on top of non-end stone blocks take longer to grow
                ModConfig.enderOrchidRegularGrowthRate : // 100/8 = 12.5%
                ModConfig.enderOrchidSpecialGrowthRate; // 100/12 = 8.3%
        if (random.nextInt(growthOdds) == 0) {
            world.setBlockState(pos, this.withAge(age + 1), 2);
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    // Doesn't break on any sort of entity collision
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {}


    // Applying bone meal makes the plant go back in age
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = Math.max(getAge(state) - getGrowthAmount(world), 0);
        world.setBlockState(pos, getDefaultState().with(getAgeProperty(), i), 2);
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 3);
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(this.getAgeProperty()) != 0;
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(state.get(getAgeProperty()) != 7) return;
        for(int i = 0; i < 2; ++i) {
            int xMultiplier = random.nextInt(2) - 1;
            int zMultiplier = random.nextInt(2) - 1;

            double x = (double)pos.getX() + 0.5 + 0.25 * (double) xMultiplier;
            double y = (float) pos.getY() + random.nextFloat();
            double z = (double)pos.getZ() + 0.5 + 0.25 * (double) zMultiplier;
            double velocityX = random.nextFloat() * (float) random.nextInt(2) * 2 - 1;
            double velocityY = ((double)random.nextFloat() - 0.5) * 0.125;
            double velocityZ = random.nextFloat() * (float) random.nextInt(2) * 2 - 1;
            world.addParticle(ParticleTypes.PORTAL, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(5, 0, 5, 11, 2, 11), // Age 0
            Block.createCuboidShape(5, 0, 5, 11, 4, 11), // Age 1
            Block.createCuboidShape(5, 0, 5, 11, 5.5, 11), // Age 2
            Block.createCuboidShape(5, 0, 5, 11, 6.5, 11), // Age 3
            Block.createCuboidShape(5, 0, 5, 11, 9, 11), // Age 4
            Block.createCuboidShape(5, 0, 5, 11, 10.5, 11), // Age 5
            Block.createCuboidShape(5, 0, 5, 11, 11, 11), // Age 6
            Block.createCuboidShape(5, 0, 5, 11, 11.5, 11) // Age 7
    };

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

}