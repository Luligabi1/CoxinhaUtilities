package me.luligabi.coxinhautilities.common.block.aquatictorch;

import com.mojang.serialization.MapCodec;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AquaticTorchBlock extends BaseTorchBlock implements SimpleWaterloggedBlock, IWittyComment {

    public AquaticTorchBlock(Properties settings) {
        super(settings);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, true));
    }

    @Override
    protected MapCodec<? extends BaseTorchBlock> codec() {
        return simpleCodec(AquaticTorchBlock::new);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.7D;
        double z = pos.getZ() + 0.5D;
        world.addParticle(Util.AQUATIC_TORCH_PARTICLE, x, y, z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        FluidState fluidState = world.getBlockState(pos).getFluidState();
        return super.canSurvive(state, world, pos) && (fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return super.getStateForPlacement(ctx).setValue(WATERLOGGED, fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
    }

    @Override
    public FluidState getFluidState(BlockState state) { return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state); }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.coxinhautilities.aquatic_torch").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.aquatic_torch.witty"));
    }

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
}