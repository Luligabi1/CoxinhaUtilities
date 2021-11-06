package me.luligabi.coxinhautilities.common;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.screenhandler.ScreenHandlingRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class CoxinhaUtilities implements ModInitializer {

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        BlockRegistry.init();

        ScreenHandlingRegistry.init();

        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, BlockRegistry.TANK_BLOCK_ENTITY);
    }

    public static final String MOD_ID = "coxinhautilities";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "item_group"))
            //.icon(() -> new ItemStack())
            .build();
}