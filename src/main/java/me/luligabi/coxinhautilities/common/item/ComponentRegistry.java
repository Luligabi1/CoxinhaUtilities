package me.luligabi.coxinhautilities.common.item;

import com.mojang.serialization.Codec;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ComponentRegistry {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, CoxinhaUtilities.MOD_ID);


    public static final Supplier<DataComponentType<Integer>> ENERGY = DATA_COMPONENT_TYPES.register(
        "energy",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(ByteBufCodecs.INT)
            .build()
    );

    public static final Supplier<DataComponentType<Boolean>> ENABLED = DATA_COMPONENT_TYPES.register(
        "enabled",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .build()
    );

}