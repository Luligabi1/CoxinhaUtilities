package me.luligabi.coxinhautilities.common.block.misc;

import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CopperLadderBlock extends LadderBlock implements WeatheringCopper, IWittyComment {

    public CopperLadderBlock(WeatherState oxidationLevel) {
        this();
        this.oxidationLevel = oxidationLevel;
        this.canOxidate = true;
    }

    public CopperLadderBlock() {
        super(FabricBlockSettings.of().strength(0.4F).sound(SoundType.COPPER).noOcclusion());
        this.oxidationLevel = WeatherState.UNAFFECTED;
        this.canOxidate = false;
    }

    private WeatherState oxidationLevel;
    private boolean canOxidate;

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if(!canOxidate) return;
        changeOverTime(state, world, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return canOxidate && WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge() {
        return oxidationLevel;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        tooltip.add(Component.translatable("tooltip.coxinhautilities.copper_ladder.1").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.coxinhautilities.copper_ladder.2").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(
                Component.translatable("tooltip.coxinhautilities.copper_ladder.witty.1"),
                Component.translatable("tooltip.coxinhautilities.copper_ladder.witty.2")
        );
    }

}