package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CardboardBoxBlock extends BlockWithEntity {

    public CardboardBoxBlock() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.CANDLE));
        setDefaultState(stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient() && player.isSneaking()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            BlockState blockState = ((CardboardBoxBlockEntity) Objects.requireNonNull(blockEntity)).blockState;
            if(blockState.isAir()) return ActionResult.FAIL;
            NbtList compound = ((CardboardBoxBlockEntity) blockEntity).nbtCopy;

            world.setBlockState(pos, blockState);
            blockEntity = world.getBlockEntity(pos);
            Objects.requireNonNull(blockEntity).readNbt(compound.getCompound(0));

            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.CARDBOARD_BOX));
            // TODO: Add sound here
            return ActionResult.SUCCESS; // FIXME: Fix unwrapping boxes opening the box's content block's UI
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        BlockState blockState = NbtHelper.toBlockState(stack.getOrCreateNbt().getCompound("BlockEntityTag").getCompound("BlockState"));

        if(!blockState.isOf(Blocks.AIR)) {
            tooltip.add(new TranslatableText(blockState.getBlock().getTranslationKey()).formatted(Formatting.GOLD));
        } else {
            tooltip.add(new TranslatableText("tooltip.coxinhautilities.empty").formatted(Formatting.GRAY));
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CardboardBoxBlockEntity(pos, state);
    }

    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

}