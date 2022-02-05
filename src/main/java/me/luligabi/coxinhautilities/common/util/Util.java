package me.luligabi.coxinhautilities.common.util;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@SuppressWarnings("UnstableApiUsage")
public class Util {

    // Converts Fabric's droplets into Forge's milliBuckets for easier displaying of liquid amounts.
    public static String getMilliBuckets(long dropletAmount) {
        if (dropletAmount == 0L) {
            return "0";
        } else if (dropletAmount < 81) {
            return "< 1";
        } else {
            return "" + dropletAmount / 81;
        }
    }

    public static ActionResult interactPlayerHand(Storage<FluidVariant> tank, PlayerEntity player, Hand hand, boolean inputEnabled, boolean outputEnabled) {
        return interactPlayerHandInner(tank, player, hand, inputEnabled, outputEnabled) ?
                ActionResult.success(player.world.isClient) : ActionResult.PASS;
    }

    private static boolean interactPlayerHandInner(Storage<FluidVariant> tank, PlayerEntity player, Hand hand, boolean inputEnabled, boolean outputEnabled) { // TODO: Add sound to transactions
        ItemStack interactionStack = player.isCreative() ? player.getStackInHand(hand).copy() : null;
        Storage<FluidVariant> handStorage = ContainerItemContext.ofPlayerHand(player, hand).find(FluidStorage.ITEM);

        // Item -> Tank action
        if(inputEnabled) {
            if (StorageUtil.move(handStorage, tank, f -> true, Long.MAX_VALUE, null) > 0) {
                if(interactionStack != null) {
                    player.setStackInHand(hand, interactionStack);
                }
                /*player.playSound(fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);*/
                return true;
            }
        }
        // Tank -> Item action
        if(outputEnabled) {
            if (StorageUtil.move(tank, handStorage, f -> true, Long.MAX_VALUE, null) > 0) {
                if(interactionStack != null) {
                    player.setStackInHand(hand, interactionStack);
                }
                //fluid.getBucketFillSound().ifPresent((sound) -> player.playSound(sound, 1.0F, 1.0F));
                return true;
            }
        }
        return false;
    }

    public static BlockEntityType<?> getTankBlockEntityType(Block block) {
        if(block == BlockRegistry.PORTABLE_TANK_MK2) return BlockEntityRegistry.PORTABLE_TANK_MK2_BLOCK_ENTITY;
        if(block == BlockRegistry.PORTABLE_TANK_MK3) return BlockEntityRegistry.PORTABLE_TANK_MK3_BLOCK_ENTITY;
        if(block == BlockRegistry.PORTABLE_TANK_MK4) return BlockEntityRegistry.PORTABLE_TANK_MK4_BLOCK_ENTITY;
        if(block == BlockRegistry.PORTABLE_TANK_MK5) return BlockEntityRegistry.PORTABLE_TANK_MK5_BLOCK_ENTITY;
        return BlockEntityRegistry.PORTABLE_TANK_MK1_BLOCK_ENTITY;
    }

    public static final DustParticleEffect AQUATIC_TORCH_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0x2f9799)), 1.0F);
}