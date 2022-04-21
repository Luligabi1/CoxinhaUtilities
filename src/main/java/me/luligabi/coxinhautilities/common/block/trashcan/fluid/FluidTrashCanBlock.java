package me.luligabi.coxinhautilities.common.block.trashcan.fluid;

import me.luligabi.coxinhautilities.common.block.trashcan.AbstractTrashCanBlock;
import me.luligabi.coxinhautilities.common.block.trashcan.energy.EnergyTrashCanBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FluidTrashCanBlock extends AbstractTrashCanBlock {

    public FluidTrashCanBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (((FluidTrashCanBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).fluidIo(player, hand) != null) {
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof EnergyTrashCanBlockEntity) {
                ItemScatterer.spawn(world, pos, (EnergyTrashCanBlockEntity) blockEntity);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidTrashCanBlockEntity(pos, state);
    }
}