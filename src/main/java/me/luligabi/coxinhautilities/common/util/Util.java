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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Predicate;

public class Util {


    public static FluidStack getFluidFromNbt(CompoundTag nbt) {
        ResourceLocation id;
        if(nbt.get("Fluid") instanceof CompoundTag tag) {
            id = ResourceLocation.parse(tag.getString("id"));
        } else {
            return FluidStack.EMPTY;
        }
        int amount = nbt.get("Fluid") instanceof CompoundTag ? ((CompoundTag) nbt.get("Fluid")).getInt("amount") : 0;

        return new FluidStack(BuiltInRegistries.FLUID.get(id), amount);
    }

    public static CompoundTag getBlockEntityData(ItemStack stack) {
        CustomData component = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if(component == null) return new CompoundTag();

        return component.copyTag();
    }

    public static void distributePowerToInventory(Player player, ItemStack startStack, int maxOutput, Predicate<ItemStack> filter) {
        IEnergyStorage startStorage = startStack.getCapability(Capabilities.EnergyStorage.ITEM);
        if(startStorage == null) return;

        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack endStack = player.getInventory().getItem(i);
            if(endStack.isEmpty() || !filter.test(endStack)) continue;

            IEnergyStorage endStorage = endStack.getCapability(Capabilities.EnergyStorage.ITEM);
            if(endStorage == null) continue;

            int newAmount = Math.min(startStorage.extractEnergy(maxOutput, true), endStorage.receiveEnergy(maxOutput, true));
            endStorage.receiveEnergy(newAmount, false);
            startStorage.extractEnergy(newAmount, false);
        }
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