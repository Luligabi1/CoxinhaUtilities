package me.luligabi.coxinhautilities.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockEntity.class)
public interface BlockEntityInvoker {

    @Invoker("loadAdditional")
    void invokeLoadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup);

}