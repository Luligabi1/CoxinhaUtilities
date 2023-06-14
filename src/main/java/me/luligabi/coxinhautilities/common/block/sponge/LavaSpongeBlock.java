package me.luligabi.coxinhautilities.common.block.sponge;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.SpongeBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LavaSpongeBlock extends SpongeBlock implements SpongeLike, IWittyComment {

    public LavaSpongeBlock() {
        super(FabricBlockSettings.copyOf(Blocks.SPONGE).mapColor(MapColor.BRIGHT_RED));
    }

    @Override
    protected void update(World world, BlockPos pos) {
        if(!absorbLiquid(world, pos, FluidTags.LAVA)) return;
        world.setBlockState(pos, BlockRegistry.WET_LAVA_SPONGE.getDefaultState(), 2);
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(Blocks.LAVA.getDefaultState()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(Text.translatable("tooltip.coxinhautilities.lava_sponge.witty"));
    }

}