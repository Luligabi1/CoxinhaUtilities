package me.luligabi.coxinhautilities.common.block.trashcan;

import com.mojang.serialization.MapCodec;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractTrashCanBlock extends BaseEntityBlock implements IWittyComment {

    public AbstractTrashCanBlock(Properties settings) {
        super(settings.requiresCorrectToolForDrops().strength(1.5F, 6.0F).pushReaction(PushReaction.IGNORE));
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private static final VoxelShape SHAPE = box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    @Override
    public List<Component> wittyComments() {
        return List.of();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AbstractTrashCanBlock::new);
    }

}