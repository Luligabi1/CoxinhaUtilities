package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class EnderOrchidBlock extends CropBlock implements IWittyComment {

    public EnderOrchidBlock() {
        super(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP));
    }


    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        if(CoxinhaUtilities.CONFIG.hasEnderOrchidStrictPlacement) {
            return floor.is(TagRegistry.ENDER_ORCHID_STRICT_PLACEMENT);
        }
        return true;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.ENDER_ORCHID_SEEDS.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int age = this.getAge(state);
        if (age >= this.getMaxAge()) return;
        int growthOdds = world.getBlockState(pos.below()).is(TagRegistry.ENDER_ORCHID_STRICT_PLACEMENT) ? // Ender Orchids planted on top of non-end stone blocks take longer to grow
                CoxinhaUtilities.CONFIG.enderOrchidRegularGrowthRate : // 100/8 = 12.5%
                CoxinhaUtilities.CONFIG.enderOrchidSpecialGrowthRate; // 100/12 = 8.3%
        if (random.nextInt(growthOdds) == 0) {
            world.setBlock(pos, this.getStateForAge(age + 1), 2);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        return mayPlaceOn(world.getBlockState(blockPos), world, blockPos);
    }

    @Override // Doesn't break on any sort of entity collision
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
    }


    @Override // Applying bone meal makes the plant go back in age
    public void growCrops(Level world, BlockPos pos, BlockState state) {
        int i = Math.max(getAge(state) - getBonemealAgeIncrease(world), 0);
        world.setBlock(pos, defaultBlockState().setValue(getAgeProperty(), i), 2);
    }

    @Override
    protected int getBonemealAgeIncrease(Level world) {
        return Mth.nextInt(world.random, 1, 2);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return state.getValue(getAgeProperty()) != 0 && state.getValue(getAgeProperty()) != 7;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if(state.getValue(getAgeProperty()) != 7) return;
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
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return AGE_TO_SHAPE[state.getValue(this.getAgeProperty())];
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.coxinhautilities.ender_orchid.1").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.coxinhautilities.ender_orchid.2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(
                Component.translatable("tooltip.coxinhautilities.ender_orchid.witty.1"),
                Component.translatable("tooltip.coxinhautilities.ender_orchid.witty.2")
        );
    }


    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[] {
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(5, 0, 5, 11, 4, 11),
            Block.box(5, 0, 5, 11, 5.5, 11),
            Block.box(5, 0, 5, 11, 6.5, 11),
            Block.box(5, 0, 5, 11, 9, 11),
            Block.box(5, 0, 5, 11, 10.5, 11),
            Block.box(5, 0, 5, 11, 11, 11),
            Block.box(5, 0, 5, 11, 11.5, 11)
    };

}