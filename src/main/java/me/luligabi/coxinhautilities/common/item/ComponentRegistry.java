package me.luligabi.coxinhautilities.common.item;

import com.mojang.serialization.Codec;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ComponentRegistry {

    public static final ComponentType<Boolean> ENABLED = register("enabled", ComponentType.<Boolean>builder()
        .codec(Codec.BOOL)
        .packetCodec(PacketCodecs.BOOL)
        .build());


    public static void init() {
        // NO-OP
    }

    private static <T> ComponentType<T> register(String id, ComponentType<T> componentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, CoxinhaUtilities.id(id), componentType);
    }

    private ComponentRegistry() {
        // NO-OP
    }

}