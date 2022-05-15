package me.luligabi.coxinhautilities.mixin;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract Vec3d applyMovementInput(Vec3d movementInput, float slipperiness);

    @Inject(at = @At("TAIL"), method = "applyMovementInput", cancellable = true)
    private void applyMovementInput(Vec3d vec3d, float f, CallbackInfoReturnable<Vec3d> infoReturnable) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        applyCopperLadderMovementSpeed(
                BlockRegistry.COPPER_LADDER, BlockRegistry.WAXED_COPPER_LADDER,
                0.5D,
                livingEntity, infoReturnable
        );
        applyCopperLadderMovementSpeed(
                BlockRegistry.EXPOSED_COPPER_LADDER, BlockRegistry.WAXED_EXPOSED_COPPER_LADDER,
                0.325D,
                livingEntity, infoReturnable
        );
        applyCopperLadderMovementSpeed(
                BlockRegistry.WEATHERED_COPPER_LADDER, BlockRegistry.WAXED_WEATHERED_COPPER_LADDER,
                0.2D,
                livingEntity, infoReturnable
        );
        applyCopperLadderMovementSpeed(
                BlockRegistry.OXIDIZED_COPPER_LADDER, BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER,
                0.15D,
                livingEntity, infoReturnable);
    }


    private void applyCopperLadderMovementSpeed(Block ladder, Block oxidizedLadder, double speed, LivingEntity livingEntity, CallbackInfoReturnable<Vec3d> infoReturnable) {
        if(livingEntity.getBlockStateAtPos().isOf(ladder) || livingEntity.getBlockStateAtPos().isOf(oxidizedLadder)) {
            if (livingEntity.horizontalCollision) {
                Vec3d velocity = new Vec3d(livingEntity.getVelocity().x, speed, livingEntity.getVelocity().z);
                infoReturnable.setReturnValue(velocity);
            }
        }
    }
}