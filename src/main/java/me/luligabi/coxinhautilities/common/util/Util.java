package me.luligabi.coxinhautilities.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Predicate;

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

    public static FluidStack getFluidFromNbt(CompoundTag nbt) {
        ResourceLocation id = ResourceLocation.parse(((CompoundTag) nbt.get("variant")).getString("fluid"));
        return new FluidStack(BuiltInRegistries.FLUID.get(id), 1);
    }

    public static CompoundTag getBlockEntityData(ItemStack stack) {
        CustomData component = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if(component == null) return new CompoundTag();

        return component.copyTag();
    }

    /*
     * This code is derivative of the one found in Tech Reborn, copyrighted by Team Reborn and licensed under MIT.
     *
     * You may see the original code here: https://github.com/TechReborn/TechReborn/blob/33da2ad59e625cdbd43624635adc65ea7bd23aa5/RebornCore/src/main/java/reborncore/common/util/ItemUtils.java#L246
     */
    public static void distributePowerToInventory(Player player, ItemStack itemStack, long maxOutput, Predicate<ItemStack> filter) {
        /*PlayerInventoryStorage playerInv = PlayerInventoryStorage.of(player);
        SingleSlotStorage<ItemVariant> sourceSlot = null;

        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i) == itemStack) {
                sourceSlot = playerInv.getSlots().get(i);
                break;
            }
        }

        if(sourceSlot == null) throw new IllegalArgumentException("Failed to locate current stack in the player inventory.");

        EnergyStorage sourceStorage = ContainerItemContext.ofPlayerSlot(player, sourceSlot).find(EnergyStorage.ITEM);
        if(sourceStorage == null) return;

        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);

            if(invStack.isEmpty() || !filter.test(invStack)) continue;

            EnergyStorageUtil.move(
                    sourceStorage,
                    ContainerItemContext.ofPlayerSlot(player, playerInv.getSlots().get(i)).find(EnergyStorage.ITEM),
                    maxOutput,
                    null
            );
        }*/
    }


    public static ItemStack singleCopy(ItemStack stack) {
        return stack.transmuteCopy(stack.getItem(), 1);
    }


    /**
     * Used to apply commas and periods to numbers according to the client's language
     * eg.:
     * (en_us) = 10000 -> 10,000
     * (pt_br) = 10000 -> 10.000
     */
    public static NumberFormat formatAccordingToLanguage() {
        Locale locale = Locale.forLanguageTag(
                Minecraft.getInstance().getLanguageManager().getSelected().replace(
                        "_",
                        "-"
                )
        );

        return NumberFormat.getNumberInstance(locale);
    }

    public static final DustParticleOptions AQUATIC_TORCH_PARTICLE = new DustParticleOptions(Vec3.fromRGB24(0x2F9799).toVector3f(), 1.0F);
}