package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CopperLadderBlock extends LadderBlock implements Oxidizable, IWittyComment {

    public CopperLadderBlock(Oxidizable.OxidationLevel oxidationLevel) {
        super(FabricBlockSettings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.COPPER).nonOpaque());
        this.oxidationLevel = oxidationLevel;
        this.canOxidate = true;
    }

    public CopperLadderBlock() {
        super(FabricBlockSettings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.COPPER).nonOpaque());
        this.oxidationLevel = OxidationLevel.UNAFFECTED;
        this.canOxidate = false;
    }

    private final Oxidizable.OxidationLevel oxidationLevel;
    private final boolean canOxidate;

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(canOxidate) {
            this.tickDegradation(state, world, pos, random);
        }
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent() && canOxidate;
    }

    public Oxidizable.OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.copper_ladder.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.copper_ladder.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<TranslatableText> wittyComments() {
        return List.of(
                new TranslatableText("tooltip.coxinhautilities.copper_ladder.witty.1"),
                new TranslatableText("tooltip.coxinhautilities.copper_ladder.witty.2")
        );
    }

}
