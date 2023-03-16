package me.luligabi.coxinhautilities.common.block.misc;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TintedPaneBlock extends PaneBlock {

    public TintedPaneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) { return false; }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) { return world.getMaxLightLevel(); }

}