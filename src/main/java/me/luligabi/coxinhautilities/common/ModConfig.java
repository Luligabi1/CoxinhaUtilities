package me.luligabi.coxinhautilities.common;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class ModConfig {

    // Ender Orchid
    @AutoGen(category = "ender_orchid")
    @TickBox
    @SerialEntry
    public boolean canGenerateEnderOrchids = true;

    @AutoGen(category = "ender_orchid")
    @MasterTickBox(value = "enderOrchidSpecialGrowthRate", invert = true)
    @SerialEntry
    public boolean hasEnderOrchidStrictPlacement = true;

    @AutoGen(category = "ender_orchid")
    @IntSlider(min = 2, max = 20, step = 2)
    @SerialEntry
    public int enderOrchidRegularGrowthRate = 8;

    @AutoGen(category = "ender_orchid")
    @IntSlider(min = 2, max = 20, step = 2)
    @SerialEntry
    public int enderOrchidSpecialGrowthRate = 12;

    // Cardboard Box
    @AutoGen(category = "cardboard_box")
    @TickBox
    @SerialEntry
    public boolean useCarrierBlacklist = true;


    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
        .id(new Identifier(CoxinhaUtilities.MOD_ID, "common"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve(String.format("%s.json", CoxinhaUtilities.MOD_ID)))
            .build()
        ).build();
}