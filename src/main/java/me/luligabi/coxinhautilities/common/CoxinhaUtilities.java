package me.luligabi.coxinhautilities.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.lootfunction.LootFunctionRegistry;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import me.luligabi.coxinhautilities.common.recipe.RecipeRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import me.luligabi.coxinhautilities.common.worldgen.FeatureRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class CoxinhaUtilities implements ModInitializer {

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        ItemRegistry.init();

        BlockRegistry.init();
        BlockEntityRegistry.init();

        RecipeRegistry.init();
        ScreenHandlingRegistry.init();

        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK1_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK2_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK3_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK4_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK5_BLOCK_ENTITY);

        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> sink.fluidStorage, BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY);

        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.FLUID_TRASH_CAN_BLOCK_ENTITY);

        EnergyStorage.SIDED.registerForBlockEntity((battery, direction) -> battery.energyStorage, BlockEntityRegistry.ENERGY_TRASH_CAN_BLOCK_ENTITY);

        FeatureRegistry.init();
        TagRegistry.init();
        LootFunctionRegistry.init();
        ItemGroupInit.init();
    }

    private static ModConfig createConfig() {
        ModConfig finalConfig;
        LOGGER.info("Trying to read config file...");
        try {
            if(CONFIG_FILE.createNewFile()) {
                LOGGER.info("No config file found, creating a new one...");
                writeConfig(GSON.toJson(JsonParser.parseString(GSON.toJson(new ModConfig()))));
                finalConfig = new ModConfig();
                LOGGER.info("Successfully created default config file.");
            } else {
                LOGGER.info("A config file was found, loading it..");
                finalConfig = GSON.fromJson(new String(Files.readAllBytes(CONFIG_FILE.toPath())), ModConfig.class);
                if(finalConfig == null) {
                    throw new NullPointerException("The config file was empty.");
                } else {
                    LOGGER.info("Successfully loaded config file.");
                }
            }
        } catch(Exception e) {
            LOGGER.error("There was an error creating/loading the config file!", e);
            finalConfig = new ModConfig();
            LOGGER.warn("Defaulting to original config.");
        }
        return finalConfig;
    }

    public static void saveConfig(ModConfig modConfig) {
        try {
            writeConfig(GSON.toJson(JsonParser.parseString(GSON.toJson(modConfig))));
            LOGGER.info("Saved new config file.");
        } catch(Exception e) {
            LOGGER.error("There was an error saving the config file!", e);
        }
    }

    private static void writeConfig(String json) {
        try(PrintWriter printWriter = new PrintWriter(CONFIG_FILE)) {
            printWriter.write(json);
            printWriter.flush();
        } catch(IOException e) {
            LOGGER.error("Failed to write config file", e);
        }
    }

    public static final String MOD_ID = "coxinhautilities";

    public static final Logger LOGGER;
    private static final Gson GSON;

    private static final File CONFIG_FILE;
    public static final ModConfig CONFIG;

    static {
        LOGGER = LoggerFactory.getLogger("Coxinha Utilities");
        GSON = new GsonBuilder().setPrettyPrinting().create();

        CONFIG_FILE = new File(String.format("%s%s%s.json", FabricLoader.getInstance().getConfigDir(), File.separator, MOD_ID));
        CONFIG = createConfig();
    }
}