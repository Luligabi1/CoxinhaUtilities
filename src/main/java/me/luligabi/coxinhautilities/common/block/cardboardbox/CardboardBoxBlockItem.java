package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CardboardBoxBlockItem extends BlockItem {

    public CardboardBoxBlockItem() {
        super(BlockRegistry.CARDBOARD_BOX, new FabricItemSettings().group(CoxinhaUtilities.ITEM_GROUP));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(!world.isClient()) {
            if (context.getPlayer().isSneaking() && blockEntity != null) {
                BlockState blockState = world.getBlockState(pos);

                NbtList nbtList = new NbtList();
                NbtCompound nbtCopy = blockEntity.createNbtWithId();
                nbtCopy.remove("id");
                nbtCopy.remove("x");
                nbtCopy.remove("y");
                nbtCopy.remove("z");
                nbtList.add(nbtCopy);

                world.setBlockState(pos, BlockRegistry.CARDBOARD_BOX.getPlacementState(new ItemPlacementContext(context)), 2); // FIXME: Fix this duplicating items
                // TODO: Sound here?
                blockEntity = world.getBlockEntity(pos); // refresh block entity
                if(blockEntity instanceof CardboardBoxBlockEntity) {
                    ((CardboardBoxBlockEntity) blockEntity).blockState = blockState;
                    ((CardboardBoxBlockEntity) blockEntity).nbtCopy = nbtList;
                    blockEntity.markDirty();
                }
                context.getStack().decrement(1);
                return ActionResult.PASS;
            }
        }
        return super.useOnBlock(context);
    }

}