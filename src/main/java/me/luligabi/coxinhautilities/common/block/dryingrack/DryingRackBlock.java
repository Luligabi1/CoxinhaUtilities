package me.luligabi.coxinhautilities.common.block.dryingrack;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DryingRackBlock extends BaseEntityBlock implements IWittyComment {

    public static final DirectionProperty FACING =  BlockStateProperties.HORIZONTAL_FACING;
    private final Map<Direction, VoxelShape> SHAPE_MAP;

    public DryingRackBlock(Properties settings) {
        super(settings);
        SHAPE_MAP = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Block.box(0, 14, 14, 16, 16, 16),
                Direction.SOUTH, Block.box(0, 14, 0, 16, 16, 2),
                Direction.WEST, Block.box(14, 14, 0, 16, 16, 16),
                Direction.EAST, Block.box(0, 14, 0, 2, 16, 16)
        ));
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if(world.isClientSide()) return InteractionResult.CONSUME;
        DryingRackBlockEntity blockEntity = (DryingRackBlockEntity) world.getBlockEntity(pos);
        ItemStack dryingItem = blockEntity.getStack();
        ItemStack handStack = player.getItemInHand(player.getUsedItemHand());

        if(dryingItem.isEmpty()) {
            if(!handStack.isEmpty()) {
                blockEntity.inventory.setItem(0, Util.singleCopy(handStack));
                handStack.shrink(1);
                blockEntity.setChanged();
                world.updateNeighbourForOutputSignal(pos, this);
                world.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        } else {
            if(handStack.isEmpty() || handStack.is(blockEntity.getStack().getItem())) {
                blockEntity.canDry = blockEntity.checkedRecipe = false;
                blockEntity.dryingTime = 0;
                blockEntity.setChanged();
                Containers.dropContents(world, pos, blockEntity.inventory);
                world.updateNeighbourForOutputSignal(pos, this);
                world.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.coxinhautilities.drying_rack").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.drying_rack.witty"));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DryingRackBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(type, BlockEntityRegistry.DRYING_RACK_BLOCK_ENTITY, DryingRackBlockEntity::tick);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(DryingRackBlock::new);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override // TODO: Improve comparator logic
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        if(world.getBlockEntity(pos) instanceof DryingRackBlockEntity dryingRackBlockEntity) {
            return dryingRackBlockEntity.inventory.isEmpty() ? 0 : 15;
        }
        return 0;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof DryingRackBlockEntity dryingRackBlockEntity ) {
                Containers.dropContents(world, pos, dryingRackBlockEntity.inventory);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, newState, moved);
        }
    }


    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE_MAP.get(state.getValue(FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();

        for(Direction direction : ctx.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                Direction oppositeDirection = direction.getOpposite();
                blockState = blockState.setValue(FACING, oppositeDirection);
                if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                    return blockState;
                }
            }
        }

        return blockState.setValue(FACING, Direction.NORTH);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}