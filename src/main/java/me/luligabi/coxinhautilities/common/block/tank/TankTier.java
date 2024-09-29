package me.luligabi.coxinhautilities.common.block.tank;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

public enum TankTier implements StringIdentifiable {

    MK1("mk1", 16 * FluidConstants.BUCKET, Formatting.GRAY, Formatting.WHITE), // Iron
    MK2("mk2", 32 * FluidConstants.BUCKET, Formatting.GOLD, Formatting.YELLOW), // Gold
    MK3("mk3", 64 * FluidConstants.BUCKET, Formatting.DARK_AQUA, Formatting.AQUA), // Diamond
    MK4("mk4", 128 * FluidConstants.BUCKET, Formatting.DARK_GREEN, Formatting.GREEN), // Emerald
    MK5("mk5", 256 * FluidConstants.BUCKET, Formatting.DARK_GRAY, Formatting.GRAY); // Netherite

    private final String name;
    private final long capacity;
    private final Formatting primaryColor;
    private final Formatting secondaryColor;

    TankTier(String name, long capacity, Formatting primaryColor, Formatting secondaryColor) {
        this.name = name;
        this.capacity = capacity;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    @Override
    public String asString() {
        return name;
    }

    public long getCapacity() {
        return capacity;
    }

    public Formatting getPrimaryColor() {
        return primaryColor;
    }

    public Formatting getSecondaryColor() {
        return secondaryColor;
    }

    public static final Codec<TankTier> CODEC = StringIdentifiable.createBasicCodec(TankTier::values);

}