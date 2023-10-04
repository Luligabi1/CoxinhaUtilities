package me.luligabi.coxinhautilities.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor {

    @Accessor("inventory")
    void coxinhautilities_setInventory(DefaultedList<ItemStack> stacks);

}