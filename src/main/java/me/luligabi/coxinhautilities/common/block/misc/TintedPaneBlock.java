package me.luligabi.coxinhautilities.common.block.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TintedPaneBlock extends IronBarsBlock {

    public TintedPaneBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) { return false; }

    @Override
    public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) { return world.getMaxLightLevel(); }

}