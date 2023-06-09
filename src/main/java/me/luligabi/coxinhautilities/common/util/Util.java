package me.luligabi.coxinhautilities.common.util;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import org.joml.Vector3f;
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


    public static ItemStack singleCopy(ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        ItemStack itemStack = new ItemStack(stack.getItem());
        itemStack.setBobbingAnimationTime(stack.getBobbingAnimationTime());
        if (stack.getNbt() != null) {
            itemStack.setNbt(stack.getNbt().copy());
        }
        return itemStack;
    }

    public static final DustParticleEffect AQUATIC_TORCH_PARTICLE = new DustParticleEffect(new Vector3f(47, 151, 153), 1.0F);
}