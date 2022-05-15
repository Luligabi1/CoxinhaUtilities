package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperLadderBlock extends LadderBlock implements IWittyComment {

    public CopperLadderBlock() {
        super(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK).nonOpaque());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.copper_ladder").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<TranslatableText> wittyComments() {
        return List.of(new TranslatableText("tooltip.coxinhautilities.copper_ladder.witty"));
    }

}
