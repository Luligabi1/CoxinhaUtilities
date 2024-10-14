package me.luligabi.coxinhautilities.mixin;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Inject(at = @At("TAIL"), method = "handleRelativeFrictionAndCalculateMovement", cancellable = true)
    private void applyMovementInput(Vec3 vec3d, float f, CallbackInfoReturnable<Vec3> infoReturnable) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        applyCopperLadderMovementSpeed(
                BlockRegistry.COPPER_LADDER.get(), BlockRegistry.WAXED_COPPER_LADDER.get(),
                0.5D,
                livingEntity, infoReturnable
        );
        applyCopperLadderMovementSpeed(
                BlockRegistry.EXPOSED_COPPER_LADDER.get(), BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get(),
                0.325D,
                livingEntity, infoReturnable
        );
        applyCopperLadderMovementSpeed(
                BlockRegistry.WEATHERED_COPPER_LADDER.get(), BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get(),
                0.2D,
                livingEntity, infoReturnable
        );
        applyCopperLadderMovementSpeed(
                BlockRegistry.OXIDIZED_COPPER_LADDER.get(), BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get(),
                0.15D,
                livingEntity, infoReturnable);
    }

    @Unique
    private void applyCopperLadderMovementSpeed(Block ladder, Block oxidizedLadder, double speed, LivingEntity livingEntity, CallbackInfoReturnable<Vec3> infoReturnable) {
        if(livingEntity.getInBlockState().is(ladder) || livingEntity.getInBlockState().is(oxidizedLadder)) {
            if (livingEntity.horizontalCollision) {
                Vec3 velocity = new Vec3(livingEntity.getDeltaMovement().x, speed, livingEntity.getDeltaMovement().z);
                infoReturnable.setReturnValue(velocity);
            }
        }
    }
}