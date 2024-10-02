package me.luligabi.coxinhautilities.common.block.tank;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.luligabi.coxinhautilities.common.util.IWittyComment;
import me.luligabi.coxinhautilities.common.util.Util;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PortableTankBlock extends BaseEntityBlock implements IWittyComment {

    private final TankTier tankTier;

    public PortableTankBlock(TankTier tankTier) {
        super(Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
        this.tankTier = tankTier;
    }

    @Override
    protected MapCodec<? extends PortableTankBlock> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                TankTier.CODEC.fieldOf("tier").forGetter(PortableTankBlock::getTankTier))
            .apply(instance, PortableTankBlock::new));
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if(((PortableTankBlockEntity) world.getBlockEntity(pos)).fluidIo(player, player.getUsedItemHand())) {
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PortableTankBlockEntity(pos, state);
    }


    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        boolean hasData = stack.get(DataComponents.BLOCK_ENTITY_DATA) != null;
        CompoundTag data = Util.getBlockEntityData(stack);

        tooltip.add(Component.translatable("tooltip.coxinhautilities.tank.fluidVariant.1")
                    .withStyle(tankTier.getPrimaryColor())
                .append(!hasData || Util.getFluidFromNbt(data).isOf(Fluids.EMPTY) ?
                        Component.translatable("tooltip.coxinhautilities.tank.none").withStyle(tankTier.getSecondaryColor()) :
                        Component.translatable("tooltip.coxinhautilities.tank.fluidVariant.2", FluidVariantAttributes.getName(Util.getFluidFromNbt(data))))
                    .withStyle(tankTier.getSecondaryColor()));

        tooltip.add(Component.translatable("tooltip.coxinhautilities.tank.capacity.1")
                    .withStyle(tankTier.getPrimaryColor())
                .append(Component.translatable("tooltip.coxinhautilities.tank.capacity.2",
                        !hasData ? "0" : String.valueOf(Screen.hasShiftDown() ? data.getLong("amount") : Util.getMilliBuckets(data.getLong("amount"))), // Current amount on tank
                        (Screen.hasShiftDown() ? tankTier.getCapacity() : Util.getMilliBuckets(tankTier.getCapacity())), // Total capacity
                        Screen.hasShiftDown() ? Component.translatable("unit.coxinhautilities.droplet") : Component.translatable("unit.coxinhautilities.milliBuckets")) // Liquid unit
                    .withStyle(tankTier.getSecondaryColor())));

        addWittyComment(tooltip);
        tooltip.add(Component.empty());
        tooltip.add((Screen.hasShiftDown() ? Component.translatable("tooltip.coxinhautilities.tank.releaseShift") : Component.translatable("tooltip.coxinhautilities.tank.holdShift")).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public List<Component> wittyComments() {
        return List.of(
                Component.translatable("tooltip.coxinhautilities.tank.witty.1"),
                Component.translatable("tooltip.coxinhautilities.tank.witty.2")
        );
    }

    public TankTier getTankTier() { return tankTier; }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    public void saveNbtToStack(BlockEntity blockEntity, ItemStack stack) {
        if (blockEntity instanceof PortableTankBlockEntity tank && tank.hasWrittenNbt()) {
            stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tank.saveWithoutMetadata(blockEntity.getLevel().registryAccess())));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) { return BOUNDING_SHAPE; }

    private static final VoxelShape BOUNDING_SHAPE = Block.box(2.5D, 0.0D, 2.5D, 13.5D, 16.0D, 13.5D);

}