package me.luligabi.coxinhautilities.common.block.trashcan;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractTrashCanBlock extends BlockWithEntity implements IWittyComment {

    public AbstractTrashCanBlock(Settings settings) {
        super(settings);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.IGNORE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    private static final VoxelShape SHAPE = createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    @Override
    public List<TranslatableText> wittyComments() {
        return null;
    }

}