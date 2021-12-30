package me.luligabi.coxinhautilities.mixin;

import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    PlayerEntity playerEntity = ((PlayerEntity) (Object) this);

    @Inject(at = @At("TAIL"), method = "eatFood")
    public void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
        if(stack.getItem() == ItemRegistry.CURSED_COXINHA && !world.isClient()) {
            int x = world.getRandom().nextInt(64);
            if(x == 0) playerEntity.damage(DamageSource.WITHER, Float.MAX_VALUE);
        }
    }
}