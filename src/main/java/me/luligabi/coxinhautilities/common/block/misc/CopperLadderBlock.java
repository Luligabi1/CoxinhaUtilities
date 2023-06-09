package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Oxidizable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperLadderBlock extends LadderBlock implements Oxidizable, IWittyComment {

    public CopperLadderBlock(Oxidizable.OxidationLevel oxidationLevel) {
        this();
        this.oxidationLevel = oxidationLevel;
        this.canOxidate = true;
    }

    public CopperLadderBlock() {
        super(FabricBlockSettings.create().strength(0.4F).sounds(BlockSoundGroup.COPPER).nonOpaque());
        this.oxidationLevel = OxidationLevel.UNAFFECTED;
        this.canOxidate = false;
    }

    private Oxidizable.OxidationLevel oxidationLevel;
    private boolean canOxidate;

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(!canOxidate) return;
        tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return canOxidate && Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    @Override
    public Oxidizable.OxidationLevel getDegradationLevel() {
        return oxidationLevel;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("tooltip.coxinhautilities.copper_ladder.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(Text.translatable("tooltip.coxinhautilities.copper_ladder.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Text> wittyComments() {
        return List.of(
                Text.translatable("tooltip.coxinhautilities.copper_ladder.witty.1"),
                Text.translatable("tooltip.coxinhautilities.copper_ladder.witty.2")
        );
    }

}