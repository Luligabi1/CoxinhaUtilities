package me.luligabi.coxinhautilities.common.block.cardboardbox;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CardboardBoxBlockEntity extends BlockEntity {

    public CardboardBoxBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CARDBOARD_BOX.get(), pos, state);
    }


    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        blockState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), nbt.getCompound("BlockState"));
        nbtCopy = nbt.getList("NbtCopy", 10);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        nbt.put("BlockState", NbtUtils.writeBlockState(blockState));
        nbt.put("NbtCopy", nbtCopy);
    }

    public boolean hasWrittenNbt() {
        return !blockState.isAir() || !nbtCopy.isEmpty();
    }

    protected BlockState blockState = Blocks.AIR.defaultBlockState();
    protected ListTag nbtCopy = new ListTag();

}