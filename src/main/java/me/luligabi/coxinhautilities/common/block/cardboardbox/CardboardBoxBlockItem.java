package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class CardboardBoxBlockItem extends BlockItem {

    public CardboardBoxBlockItem() {
        super(BlockRegistry.CARDBOARD_BOX, new Item.Settings());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Optional<BlockEntity> blockEntity = Optional.ofNullable(world.getBlockEntity(pos));

        if(context.getPlayer().isSneaking()) {
            BlockState blockState = world.getBlockState(pos);
            if(blockState.getBlock().getHardness() >= 0.01F && isNbtBlockAir(context.getStack()) && !blockState.isIn(TagRegistry.UNBOXABLE) && !isOnCarrierBlackList(blockState)) {
                if(blockEntity.isPresent() && hasLootTable(blockEntity.get())) return super.useOnBlock(context);

                if(context.getWorld().isClient()) return ActionResult.CONSUME;

                NbtList nbtList = new NbtList();
                if(blockEntity.isPresent()) {
                    NbtCompound nbtCopy = blockEntity.get().createNbtWithId(world.getRegistryManager());
                    nbtCopy.remove("id");
                    nbtCopy.remove("x");
                    nbtCopy.remove("y");
                    nbtCopy.remove("z");
                    nbtList.add(nbtCopy);

                    // Desperate attempt to cover every edge case :)
                    Clearable.clear(blockEntity);
                    world.removeBlockEntity(pos);
                }
                world.setBlockState(pos, BlockRegistry.CARDBOARD_BOX.getPlacementState(new ItemPlacementContext(context)), 32);
                world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.BLOCKS, 1F, 1F); // FIXME use unique soundevent

                blockEntity = Optional.ofNullable(world.getBlockEntity(pos)); // refresh block entity
                if(blockEntity.isPresent() && blockEntity.get() instanceof CardboardBoxBlockEntity cardboardBoxBE) {
                    cardboardBoxBE.blockState = blockState;
                    cardboardBoxBE.nbtCopy = nbtList;
                    blockEntity.get().markDirty();
                }
                context.getStack().decrement(1);
                return ActionResult.CONSUME;
            }
        }
        return super.useOnBlock(context);
    }

    private boolean isOnCarrierBlackList(BlockState blockState) {
        if(!CoxinhaUtilities.CONFIG.useCarrierBlacklist) return false;
        return blockState.isIn(TagRegistry.CARRIER_BLACKLIST);
    }

    private boolean hasLootTable(BlockEntity blockEntity) {
        if(blockEntity instanceof LootableContainerBlockEntity lootableContainer) {
            return lootableContainer.getLootTable() != null;
        }
        return false;
    }

    private boolean isNbtBlockAir(ItemStack stack) {
        if(stack.get(DataComponentTypes.BLOCK_ENTITY_DATA) == null) return true;

        NbtCompound data = Util.getBlockEntityData(stack);
        return NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), data.getCompound("BlockState")).isAir();
    }

}