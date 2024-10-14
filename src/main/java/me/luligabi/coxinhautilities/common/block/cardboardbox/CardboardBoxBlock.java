package me.luligabi.coxinhautilities.common.block.cardboardbox;

import com.mojang.serialization.MapCodec;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import me.luligabi.coxinhautilities.mixin.BlockEntityInvoker;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CardboardBoxBlock extends BaseEntityBlock implements IWittyComment {

    public CardboardBoxBlock(Properties settings) {
        super(settings);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CardboardBoxBlock::new);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if((player.isShiftKeyDown() || !player.getOffhandItem().isEmpty())) { // allows unwrapping boxes with a block/shield on offhand
            Optional<BlockEntity> blockEntity = Optional.ofNullable(world.getBlockEntity(pos));
            BlockState blockState = blockEntity.isPresent() ? ((CardboardBoxBlockEntity) blockEntity.get()).blockState : state;
            if(state.isAir()) return InteractionResult.FAIL;

            if(world.isClientSide()) return InteractionResult.CONSUME;
            ListTag compound = blockEntity.map(entity -> ((CardboardBoxBlockEntity) entity).nbtCopy).orElse(null);

            world.setBlockAndUpdate(pos, getPlacementState(blockState, state.getValue(FACING).getOpposite()));
            blockEntity = Optional.ofNullable(world.getBlockEntity(pos));
            blockEntity.ifPresent(entity -> ((BlockEntityInvoker) entity).invokeLoadAdditional(compound.getCompound(0), world.registryAccess()));

            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockRegistry.CARDBOARD_BOX.get()));
            world.playSound(null, pos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1F, 1F);
            return InteractionResult.CONSUME;
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        if(stack.get(DataComponents.BLOCK_ENTITY_DATA) != null) {
            CompoundTag data = Util.getBlockEntityData(stack);

            BlockState blockState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), data.getCompound("BlockState"));

            if(!blockState.is(Blocks.AIR)) {
                tooltip.add(Component.translatable(blockState.getBlock().getDescriptionId()).withStyle(ChatFormatting.GOLD));
            }
        } else {
            tooltip.add(Component.translatable("tooltip.coxinhautilities.empty").withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.coxinhautilities.cardboard_box.1").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.coxinhautilities.cardboard_box.2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.coxinhautilities.cardboard_box.3").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        addWittyComment(tooltip);
    }

    private BlockState getPlacementState(BlockState original, Direction cardboardDirection) {
        DirectionProperty property = (DirectionProperty) original.getProperties()
                .stream()
                .filter(c -> c == BlockStateProperties.HORIZONTAL_FACING || c == BlockStateProperties.FACING)
                .findFirst()
                .orElse(null);
        if(property == null) return original;
        return original.trySetValue(property, cardboardDirection);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void saveNbtToStack(BlockEntity blockEntity, ItemStack stack) {
        if (blockEntity instanceof CardboardBoxBlockEntity cardboardBox && cardboardBox.hasWrittenNbt()) {
            BlockItem.setBlockEntityData(stack, cardboardBox.getType(), cardboardBox.saveWithoutMetadata(cardboardBox.getLevel().registryAccess()));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CardboardBoxBlockEntity(pos, state);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.cardboard_box.witty"));
    }

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

}