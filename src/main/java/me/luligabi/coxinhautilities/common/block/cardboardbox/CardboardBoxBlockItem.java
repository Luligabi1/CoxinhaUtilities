package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;

public class CardboardBoxBlockItem extends BlockItem {

    public CardboardBoxBlockItem() {
        super(BlockRegistry.CARDBOARD_BOX.get(), new Properties());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Optional<BlockEntity> blockEntity = Optional.ofNullable(world.getBlockEntity(pos));

        if(context.getPlayer().isShiftKeyDown()) {
            BlockState blockState = world.getBlockState(pos);
            if(blockState.getBlock().defaultDestroyTime() >= 0.01F && isNbtBlockAir(context.getItemInHand()) && !blockState.is(TagRegistry.UNBOXABLE) && !blockState.is(Tags.Blocks.RELOCATION_NOT_SUPPORTED)) {
                if(blockEntity.isPresent() && hasLootTable(blockEntity.get())) return super.useOn(context);

                if(context.getLevel().isClientSide()) return InteractionResult.CONSUME;

                ListTag nbtList = new ListTag();
                if(blockEntity.isPresent()) {
                    CompoundTag nbtCopy = blockEntity.get().saveWithId(world.registryAccess());
                    nbtCopy.remove("id");
                    nbtCopy.remove("x");
                    nbtCopy.remove("y");
                    nbtCopy.remove("z");
                    nbtList.add(nbtCopy);

                    // Desperate attempt to cover every edge case :)
                    Clearable.tryClear(blockEntity);
                    world.removeBlockEntity(pos);
                }
                world.setBlock(pos, BlockRegistry.CARDBOARD_BOX.get().getStateForPlacement(new BlockPlaceContext(context)), 32);
                world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_LEATHER.value(), SoundSource.BLOCKS, 1F, 1F); // FIXME use unique soundevent

                blockEntity = Optional.ofNullable(world.getBlockEntity(pos)); // refresh block entity
                if(blockEntity.isPresent() && blockEntity.get() instanceof CardboardBoxBlockEntity cardboardBoxBE) {
                    cardboardBoxBE.blockState = blockState;
                    cardboardBoxBE.nbtCopy = nbtList;
                    blockEntity.get().setChanged();
                }
                context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return super.useOn(context);
    }


    private boolean hasLootTable(BlockEntity blockEntity) {
        if(blockEntity instanceof RandomizableContainerBlockEntity lootableContainer) {
            return lootableContainer.getLootTable() != null;
        }
        return false;
    }

    private boolean isNbtBlockAir(ItemStack stack) {
        if(stack.get(DataComponents.BLOCK_ENTITY_DATA) == null) return true;

        CompoundTag data = Util.getBlockEntityData(stack);
        return NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), data.getCompound("BlockState")).isAir();
    }

}