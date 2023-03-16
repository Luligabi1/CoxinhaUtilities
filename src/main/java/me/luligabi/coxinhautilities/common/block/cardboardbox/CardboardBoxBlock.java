package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class CardboardBoxBlock extends BlockWithEntity implements IWittyComment {

    public CardboardBoxBlock() {
        super(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(0.5F).sounds(BlockSoundGroup.WOOD));
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
            world.playSound(null, pos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 1F, 1F);
            return ActionResult.SUCCESS; // FIXME: Fix unwrapping boxes opening the box's content block's UI
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        BlockState blockState = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), stack.getOrCreateNbt().getCompound("BlockEntityTag").getCompound("BlockState"));

        if(!blockState.isOf(Blocks.AIR)) {
            tooltip.add(Text.translatable(blockState.getBlock().getTranslationKey()).formatted(Formatting.GOLD));
        } else {
            tooltip.add(Text.translatable("tooltip.coxinhautilities.empty").formatted(Formatting.GRAY));
        }
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("tooltip.coxinhautilities.cardboard_box.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.cardboard_box.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.empty());
        tooltip.add(Text.translatable("tooltip.coxinhautilities.cardboard_box.3").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerLookDirection());
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

    public void saveNbtToStack(BlockEntity blockEntity, ItemStack stack) {
        if (blockEntity instanceof CardboardBoxBlockEntity cardboardBox && cardboardBox.hasWrittenNbt()) {
            BlockItem.setBlockEntityNbt(stack, cardboardBox.getType(), cardboardBox.createNbt());
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CardboardBoxBlockEntity(pos, state);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(Text.translatable("tooltip.coxinhautilities.cardboard_box.witty"));
    }

    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

}