package me.luligabi.coxinhautilities.common;

import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class CoxinhaUtilities implements ModInitializer {

    @Override
    public void onInitialize() {
        BlockRegistry.init();
    }

    public static final String MOD_ID = "coxinhautilities";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier(MOD_ID, "item_group"))
            //.icon(() -> new ItemStack())
            .build();
}