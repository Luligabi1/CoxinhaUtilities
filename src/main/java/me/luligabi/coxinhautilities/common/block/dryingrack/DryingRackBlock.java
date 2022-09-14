package me.luligabi.coxinhautilities.common.block.dryingrack;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DryingRackBlock extends BlockWithEntity implements IWittyComment {

    public static final DirectionProperty FACING =  Properties.HORIZONTAL_FACING;
    private final Map<Direction, VoxelShape> SHAPE_MAP;

    public DryingRackBlock(Settings settings) {
        super(settings);
        SHAPE_MAP = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Block.createCuboidShape(0, 14, 14, 16, 16, 16),
                Direction.SOUTH, Block.createCuboidShape(0, 14, 0, 16, 16, 2),
                Direction.WEST, Block.createCuboidShape(14, 14, 0, 16, 16, 16),
                Direction.EAST, Block.createCuboidShape(0, 14, 0, 2, 16, 16)
        ));
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient()) return ActionResult.CONSUME;
        DryingRackBlockEntity blockEntity = (DryingRackBlockEntity) world.getBlockEntity(pos);
        ItemStack dryingItem = blockEntity.getInventory().get(0);
        ItemStack handStack = player.getStackInHand(hand);

        if(dryingItem.isEmpty()) {
            if(!handStack.isEmpty()) {
                blockEntity.getInventory().set(0, Util.singleCopy(handStack));
                handStack.decrement(1);
                blockEntity.markDirty();
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
        } else {
            if(handStack.isEmpty()) {
                blockEntity.canDry = false;
                blockEntity.checkedRecipe = false;
                blockEntity.dryingTime = 0;
                blockEntity.markDirty();
                ItemScatterer.spawn(world, pos, blockEntity);
                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.coxinhautilities.drying_rack").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(Text.translatable("tooltip.coxinhautilities.drying_rack.witty"));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DryingRackBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, BlockEntityRegistry.DRYING_RACK_BLOCK_ENTITY, DryingRackBlockEntity::tick);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DryingRackBlockEntity) {
                ItemScatterer.spawn(world, pos, (DryingRackBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_MAP.get(state.get(FACING));
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.offset(state.get(FACING).getOpposite())).getMaterial().isSolid();
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();

        for(Direction direction : ctx.getPlacementDirections()) {
            if (direction.getAxis().isHorizontal()) {
                Direction oppositeDirection = direction.getOpposite();
                blockState = blockState.with(FACING, oppositeDirection);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState;
                }
            }
        }

        return null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}