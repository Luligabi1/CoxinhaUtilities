package me.luligabi.coxinhautilities.mixin;

import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "eatFood")
    public void eatFood(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if(stack.getItem() == ItemRegistry.CURSED_COXINHA && !world.isClient()) {
            PlayerEntity playerEntity = ((PlayerEntity) (Object) this);

            int x = world.getRandom().nextInt(64);
            if(x == 0) playerEntity.damage(playerEntity.getDamageSources().wither(), Float.MAX_VALUE);
        }
    }
}