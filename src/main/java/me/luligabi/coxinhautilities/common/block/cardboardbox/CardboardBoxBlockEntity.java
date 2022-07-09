package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

public class CardboardBoxBlockEntity extends BlockEntity {

    public CardboardBoxBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CARDBOARD_BOX_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        blockState = NbtHelper.toBlockState(nbt.getCompound("BlockState"));
        nbtCopy = nbt.getList("NbtCopy", 10);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("BlockState", NbtHelper.fromBlockState(blockState));
        nbt.put("NbtCopy", nbtCopy);
    }

    public boolean hasWrittenNbt() {
        return !blockState.isAir() || !nbtCopy.isEmpty();
    }

    protected BlockState blockState = Blocks.AIR.getDefaultState();
    protected NbtList nbtCopy = new NbtList();

}