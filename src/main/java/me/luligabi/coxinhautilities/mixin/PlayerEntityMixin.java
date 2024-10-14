package me.luligabi.coxinhautilities.mixin;

import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @Inject(at = @At("TAIL"), method = "eat")
    public void eatFood(Level world, ItemStack stack, FoodProperties foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if(stack.getItem() == ItemRegistry.CURSED_COXINHA && !world.isClientSide()) {
            Player playerEntity = ((Player) (Object) this);

            int x = world.getRandom().nextInt(64);
            if(x == 0) playerEntity.hurt(playerEntity.damageSources().wither(), Float.MAX_VALUE);
        }
    }
}