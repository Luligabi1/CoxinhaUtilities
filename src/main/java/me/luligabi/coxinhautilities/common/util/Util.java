package me.luligabi.coxinhautilities.common.util;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.text.NumberFormat;
import java.util.Locale;
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

    public static FluidVariant getFluidFromNbt(NbtCompound nbt) {
        Identifier id = Identifier.of(((NbtCompound) nbt.get("variant")).getString("fluid"));
        return FluidVariant.of(Registries.FLUID.get(id));
    }

    public static NbtCompound getBlockEntityData(ItemStack stack) {
        NbtComponent component = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
        if(component == null) return new NbtCompound();

        return component.copyNbt();
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
        return stack.copyComponentsToNewStack(stack.getItem(), 1);
    }


    /**
     * Used to apply commas and periods to numbers according to the client's language
     * eg.:
     * (en_us) = 10000 -> 10,000
     * (pt_br) = 10000 -> 10.000
     */
    public static NumberFormat formatAccordingToLanguage() {
        Locale locale = Locale.forLanguageTag(
                MinecraftClient.getInstance().getLanguageManager().getLanguage().replace(
                        "_",
                        "-"
                )
        );

        return NumberFormat.getNumberInstance(locale);
    }

    public static final DustParticleEffect AQUATIC_TORCH_PARTICLE = new DustParticleEffect(Vec3d.unpackRgb(0x2F9799).toVector3f(), 1.0F);
}