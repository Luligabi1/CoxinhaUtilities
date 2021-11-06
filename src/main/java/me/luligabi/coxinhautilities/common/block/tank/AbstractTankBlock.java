package me.luligabi.coxinhautilities.common.block.tank;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class AbstractTankBlock extends BlockWithEntity {

    public AbstractTankBlock(Settings settings) {
        super(settings);
    } // TODO: Add capacity param.

    // TODO: Add way to insert via items (buckets, bottles, etc)
    // TODO: Add bucket mode

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AbstractTankBlockEntity(pos, state);
    }


    @Override // TODO: Properly show the amount of liquid inside.
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(stack.getNbt() != null) {
            tooltip.add(new LiteralText("amount: " + stack.getNbt().getLong("amount")));
        }
    }
}