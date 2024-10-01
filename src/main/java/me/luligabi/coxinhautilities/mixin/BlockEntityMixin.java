package me.luligabi.coxinhautilities.mixin;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

    /*
     * Before 1.21, #supports used the #getType() method to check, but now uses the type field directly.
     * This breaks Wooden Hoppers since their type is only defined through the #getType() method.
     */
    @Inject(
        method = "supports",
        at = @At("HEAD"),
        cancellable = true
    )
    void coxinhautilities_supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        BlockEntity blockEntity = ((BlockEntity) (Object) this);
        if(blockEntity.getType() == BlockEntityRegistry.WOODEN_HOPPER_ENTITY) {
            cir.setReturnValue(blockEntity.getType().supports(state));
        }
    }

}