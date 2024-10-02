package me.luligabi.coxinhautilities.common.block.tank;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;

public enum TankTier implements StringRepresentable {

    MK1("mk1", 16 * FluidConstants.BUCKET, ChatFormatting.GRAY, ChatFormatting.WHITE), // Iron
    MK2("mk2", 32 * FluidConstants.BUCKET, ChatFormatting.GOLD, ChatFormatting.YELLOW), // Gold
    MK3("mk3", 64 * FluidConstants.BUCKET, ChatFormatting.DARK_AQUA, ChatFormatting.AQUA), // Diamond
    MK4("mk4", 128 * FluidConstants.BUCKET, ChatFormatting.DARK_GREEN, ChatFormatting.GREEN), // Emerald
    MK5("mk5", 256 * FluidConstants.BUCKET, ChatFormatting.DARK_GRAY, ChatFormatting.GRAY); // Netherite

    private final String name;
    private final long capacity;
    private final ChatFormatting primaryColor;
    private final ChatFormatting secondaryColor;

    TankTier(String name, long capacity, ChatFormatting primaryColor, ChatFormatting secondaryColor) {
        this.name = name;
        this.capacity = capacity;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

    public long getCapacity() {
        return capacity;
    }

    public ChatFormatting getPrimaryColor() {
        return primaryColor;
    }

    public ChatFormatting getSecondaryColor() {
        return secondaryColor;
    }

    public static final Codec<TankTier> CODEC = StringRepresentable.fromValues(TankTier::values);

}