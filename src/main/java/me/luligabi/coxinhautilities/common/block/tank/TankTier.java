package me.luligabi.coxinhautilities.common.block.tank;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.util.Formatting;

@SuppressWarnings("UnstableApiUsage")
public enum TankTier {

    MK1(16 * FluidConstants.BUCKET, Formatting.GRAY, Formatting.WHITE), // Iron
    MK2(32 * FluidConstants.BUCKET, Formatting.GOLD, Formatting.YELLOW), // Gold
    MK3(64 * FluidConstants.BUCKET, Formatting.DARK_AQUA, Formatting.AQUA), // Diamond
    MK4(128 * FluidConstants.BUCKET, Formatting.DARK_GREEN, Formatting.GREEN), // Emerald
    MK5(256 * FluidConstants.BUCKET, Formatting.DARK_GRAY, Formatting.GRAY); // Netherite

    private final long capacity;
    private final Formatting primaryColor;
    private final Formatting secondaryColor;

    TankTier(long capacity, Formatting primaryColor, Formatting secondaryColor) {
        this.capacity = capacity;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public long getCapacity() { return capacity; }

    public Formatting getPrimaryColor() { return primaryColor; }

    public Formatting getSecondaryColor() { return secondaryColor; }

}