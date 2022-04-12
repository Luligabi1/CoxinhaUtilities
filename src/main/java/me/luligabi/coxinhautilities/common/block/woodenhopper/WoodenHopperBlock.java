package me.luligabi.coxinhautilities.common.block.woodenhopper;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WoodenHopperBlock extends HopperBlock implements IWittyComment {

    public WoodenHopperBlock(Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WoodenHopperBlockEntity) {
                player.openHandledScreen((WoodenHopperBlockEntity) blockEntity);
                player.incrementStat(Stats.USED.getOrCreateStat(this.asItem()));
            }
            return ActionResult.CONSUME;
        } else {
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenHopperBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient ? checkType(type, BlockEntityRegistry.WOODEN_HOPPER_ENTITY, HopperBlockEntity::serverTick) : null;
    }

    // Overridden to disable redstone behavior.
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) { }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) { }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.wooden_hopper.1").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        tooltip.add(new LiteralText(""));
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.wooden_hopper.2").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        addWittyComment(tooltip);
    }

    @Override
    public List<TranslatableText> wittyComments() {
        List<TranslatableText> wittyComments = new ArrayList<>();
        wittyComments.add(new TranslatableText("tooltip.coxinhautilities.wooden_hopper.witty"));
        return wittyComments;
    }
}