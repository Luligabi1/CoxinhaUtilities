package me.luligabi.coxinhautilities.common.block.trashcan.fluid;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluidTrashCanBlock extends AbstractTrashCanBlock {

    public FluidTrashCanBlock() {
        super(Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if(((FluidTrashCanBlockEntity) world.getBlockEntity(pos)).fluidIo(player, player.getUsedItemHand())) {
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        if(world.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FluidTrashCanBlockEntity) {
            player.openMenu((FluidTrashCanBlockEntity) blockEntity);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof FluidTrashCanBlockEntity) {
                Containers.dropContents(world, pos, (FluidTrashCanBlockEntity) blockEntity);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.coxinhautilities.fluid_trash_can.1").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.coxinhautilities.fluid_trash_can.2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(Component.translatable("tooltip.coxinhautilities.fluid_trash_can.witty"));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FluidTrashCanBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(type, BlockEntityRegistry.FLUID_TRASH_CAN.get(), FluidTrashCanBlockEntity::tick);
    }

}