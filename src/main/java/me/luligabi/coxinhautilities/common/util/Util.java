package me.luligabi.coxinhautilities.common.util;

import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

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

    public static final DustParticleEffect AQUATIC_TORCH_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb(0x2f9799)), 1.0F);
}