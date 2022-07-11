package me.luligabi.coxinhautilities.common.util;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.function.Predicate;

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

    /*
     * This code is derivative of the one found in Tech Reborn, copyrighted by Team Reborn and licensed under MIT.
     *
     * You may see the original code here: https://github.com/TechReborn/TechReborn/blob/33da2ad59e625cdbd43624635adc65ea7bd23aa5/RebornCore/src/main/java/reborncore/common/util/ItemUtils.java#L246
     */
    public static void distributePowerToInventory(PlayerEntity player, ItemStack itemStack, long maxOutput, Predicate<ItemStack> filter) {
        PlayerInventoryStorage playerInv = PlayerInventoryStorage.of(player);
        SingleSlotStorage<ItemVariant> sourceSlot = null;

        for(int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i) == itemStack) {
                sourceSlot = playerInv.getSlots().get(i);
                break;
            }
        }

        if(sourceSlot == null) throw new IllegalArgumentException("Failed to locate current stack in the player inventory.");

        EnergyStorage sourceStorage = ContainerItemContext.ofPlayerSlot(player, sourceSlot).find(EnergyStorage.ITEM);
        if(sourceStorage == null) return;

        for(int i = 0; i < player.getInventory().size(); i++) {
            ItemStack invStack = player.getInventory().getStack(i);

            if(invStack.isEmpty() || !filter.test(invStack)) continue;

            EnergyStorageUtil.move(
                    sourceStorage,
                    ContainerItemContext.ofPlayerSlot(player, playerInv.getSlots().get(i)).find(EnergyStorage.ITEM),
                    maxOutput,
                    null
            );
        }
    }

    public static BlockEntityType<?> getTankBlockEntityType(Block block) {
        if(block == BlockRegistry.PORTABLE_TANK_MK2) return BlockEntityRegistry.PORTABLE_TANK_MK2_BLOCK_ENTITY;
        if(block == BlockRegistry.PORTABLE_TANK_MK3) return BlockEntityRegistry.PORTABLE_TANK_MK3_BLOCK_ENTITY;
        if(block == BlockRegistry.PORTABLE_TANK_MK4) return BlockEntityRegistry.PORTABLE_TANK_MK4_BLOCK_ENTITY;
        if(block == BlockRegistry.PORTABLE_TANK_MK5) return BlockEntityRegistry.PORTABLE_TANK_MK5_BLOCK_ENTITY;
        return BlockEntityRegistry.PORTABLE_TANK_MK1_BLOCK_ENTITY;
    }

    public static ItemStack singleCopy(ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        ItemStack itemStack = new ItemStack(stack.getItem());
        itemStack.setBobbingAnimationTime(stack.getBobbingAnimationTime());
        if (stack.getNbt() != null) {
            itemStack.setNbt(stack.getNbt().copy());
        }
        return itemStack;
    }

    public static final DustParticleEffect AQUATIC_TORCH_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0x2f9799)), 1.0F);
}