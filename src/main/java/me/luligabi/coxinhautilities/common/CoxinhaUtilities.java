package me.luligabi.coxinhautilities.common;

import me.luligabi.coxinhautilities.common.block.BlockEntityRegistry;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CoxinhaUtilities implements ModInitializer {

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        ItemRegistry.init();

        BlockRegistry.init();
        BlockEntityRegistry.init();
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(MOD_ID, "tank_copy_data"), TANK_COPY_DATA);

        ScreenHandlingRegistry.init();

        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK1_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK2_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK3_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK4_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockEntityRegistry.PORTABLE_TANK_MK5_BLOCK_ENTITY);

        FluidStorage.SIDED.registerForBlockEntity((sink, direction) -> sink.fluidStorage, BlockEntityRegistry.GRANNYS_SINK_BLOCK_ENTITY);
    }

    public static final String MOD_ID = "coxinhautilities";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "item_group"))
            .icon(() -> new ItemStack(ItemRegistry.COXINHA))
            .build();

    public static final LootFunctionType TANK_COPY_DATA = new LootFunctionType(new TankCopyDataLootFunction.TankCopyDataLootFunctionSerializer());
}