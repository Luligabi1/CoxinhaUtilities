package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.util.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlock extends BlockWithEntity {

    public PortableTankBlock(TankTier tankTier) {
        super(FabricBlockSettings.of(Material.METAL).strength(2.0F, 3.0F).sounds(BlockSoundGroup.METAL));
        this.tankTier = tankTier;
    }

    private final TankTier tankTier;

    // TODO: Add way to insert via items (buckets, bottles, etc)
    // TODO: Add bucket mode

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PortableTankBlockEntity(pos, state);
    }


    public TankTier getTankTier() { return tankTier; }

    @Override // TODO: Fix Formatting being incorrect (one color only)
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.tank.fluidVariant").formatted(tankTier.getPrimaryColor())
                .append(stack.getNbt() == null ? new TranslatableText("tooltip.coxinhautilities.tank.none") : FluidVariantRendering.getName(FluidVariant.fromNbt(stack.getNbt().getCompound("BlockEntityTag").getCompound("fluidVariant")))).formatted(tankTier.getSecondaryColor()));

        tooltip.add(new TranslatableText("tooltip.coxinhautilities.tank.capacity").formatted(tankTier.getPrimaryColor())
                .append(stack.getNbt() == null ? "0" : String.valueOf(options.isAdvanced() ? stack.getNbt().getCompound("BlockEntityTag").getLong("amount") : Util.getMilliBuckets(stack.getNbt().getCompound("BlockEntityTag").getLong("amount")))) // amount
                .append("/" + (options.isAdvanced() ? tankTier.getCapacity() : Util.getMilliBuckets(tankTier.getCapacity()))) // capacity
                .append(options.isAdvanced() ? new TranslatableText("unit.coxinhautilities.droplet") : new TranslatableText("unit.coxinhautilities.milliBuckets")).formatted(tankTier.getSecondaryColor())); // fluid unit
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOUNDING_SHAPE;
    }

    private static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D);
}