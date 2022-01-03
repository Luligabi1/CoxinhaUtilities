package me.luligabi.coxinhautilities.common.block.tank;

import me.luligabi.coxinhautilities.common.util.Util;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class PortableTankBlock extends BlockWithEntity {

    public PortableTankBlock(TankTier tankTier) {
        super(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool().sounds(BlockSoundGroup.METAL));
        this.tankTier = tankTier;
    }

    private final TankTier tankTier;

    //TODO: Fluids should render inside the tank on inventory too

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (((PortableTankBlockEntity) world.getBlockEntity(pos)).onPlayerUse(player)) {
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PortableTankBlockEntity(pos, state);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("tooltip.coxinhautilities.tank.fluidVariant.1")
                    .formatted(tankTier.getPrimaryColor())
                .append(!stack.hasNbt() ?
                        new TranslatableText("tooltip.coxinhautilities.tank.none").formatted(tankTier.getSecondaryColor()) :
                        new TranslatableText("tooltip.coxinhautilities.tank.fluidVariant.2", FluidVariantRendering.getName(FluidVariant.fromNbt(stack.getNbt().getCompound("BlockEntityTag").getCompound("fluidVariant"))))
                    .formatted(tankTier.getSecondaryColor())));

        tooltip.add(new TranslatableText("tooltip.coxinhautilities.tank.capacity.1")
                    .formatted(tankTier.getPrimaryColor())
                .append(new TranslatableText("tooltip.coxinhautilities.tank.capacity.2",
                        !stack.hasNbt() ? "0" : String.valueOf(Screen.hasShiftDown() ? stack.getNbt().getCompound("BlockEntityTag").getLong("amount") : Util.getMilliBuckets(stack.getNbt().getCompound("BlockEntityTag").getLong("amount"))), // Current amount on tank
                        (Screen.hasShiftDown() ? tankTier.getCapacity() : Util.getMilliBuckets(tankTier.getCapacity())), // Total capacity
                        Screen.hasShiftDown() ? new TranslatableText("unit.coxinhautilities.droplet") : new TranslatableText("unit.coxinhautilities.milliBuckets")) // Liquid unit
                    .formatted(tankTier.getSecondaryColor())));

        tooltip.add(new TranslatableText("tooltip.coxinhautilities.tank.bucketMode")
                    .formatted(tankTier.getPrimaryColor())
                .append(!stack.hasNbt() || !stack.getNbt().getCompound("BlockEntityTag").getBoolean("isBucketMode") ?
                        new TranslatableText("tooltip.coxinhautilities.tank.bucketMode.off").formatted(tankTier.getSecondaryColor()) :
                        new TranslatableText("tooltip.coxinhautilities.tank.bucketMode.on")
                    .formatted(tankTier.getSecondaryColor())));

        tooltip.add(new LiteralText(" "));
        tooltip.add((Screen.hasShiftDown() ? new TranslatableText("tooltip.coxinhautilities.tank.releaseShift") : new TranslatableText("tooltip.coxinhautilities.tank.holdShift")).formatted(Formatting.GRAY, Formatting.ITALIC));
    }

    public TankTier getTankTier() { return tankTier; }

    @Override
    public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return BOUNDING_SHAPE; }

    private static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D);
}