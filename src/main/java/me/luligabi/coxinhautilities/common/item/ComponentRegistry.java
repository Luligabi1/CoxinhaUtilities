package me.luligabi.coxinhautilities.common.item;

import com.mojang.serialization.Codec;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

public class ComponentRegistry {

    public static final DataComponentType<Boolean> ENABLED = register("enabled", DataComponentType.<Boolean>builder()
        .persistent(Codec.BOOL)
        .networkSynchronized(ByteBufCodecs.BOOL)
        .build());


    public static void init() {
        // NO-OP
    }

    private static <T> DataComponentType<T> register(String id, DataComponentType<T> componentType) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CoxinhaUtilities.id(id), componentType);
    }

    private ComponentRegistry() {
        // NO-OP
    }

}