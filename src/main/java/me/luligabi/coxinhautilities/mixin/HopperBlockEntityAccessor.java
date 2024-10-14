package me.luligabi.coxinhautilities.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor {

    @Accessor("items")
    void coxinhautilities_setInventory(NonNullList<ItemStack> stacks);

}