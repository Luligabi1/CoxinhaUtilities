package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class ItemGroupInit {

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries ->
                entries.addAfter(new ItemStack(Items.COOKED_CHICKEN), List.of(
                        new ItemStack(ItemRegistry.COXINHA),
                        new ItemStack(ItemRegistry.CURSED_COXINHA),
                        new ItemStack(ItemRegistry.GOLDEN_COXINHA),
                        new ItemStack(ItemRegistry.DIAMOND_COXINHA)
                ))
        );

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
                entries.addBefore(new ItemStack(Items.CHORUS_PLANT), new ItemStack(ItemRegistry.ENDER_ORCHID_SEEDS));
                entries.addAfter(new ItemStack(Items.WET_SPONGE), List.of(
                    new ItemStack(BlockRegistry.LAVA_SPONGE),
                    new ItemStack(BlockRegistry.WET_LAVA_SPONGE)
                ));
            }
        );

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries ->
                entries.addBefore(new ItemStack(Items.HOPPER), new ItemStack(BlockRegistry.WOODEN_HOPPER))
        );

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
                entries.addAfter(new ItemStack(Items.SOUL_TORCH), new ItemStack(BlockRegistry.AQUATIC_TORCH));
                entries.addAfter(new ItemStack(Items.LADDER), List.of(
                    new ItemStack(BlockRegistry.COPPER_LADDER),
                    new ItemStack(BlockRegistry.EXPOSED_COPPER_LADDER),
                    new ItemStack(BlockRegistry.WEATHERED_COPPER_LADDER),
                    new ItemStack(BlockRegistry.OXIDIZED_COPPER_LADDER),

                    new ItemStack(BlockRegistry.WAXED_COPPER_LADDER),
                    new ItemStack(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER),
                    new ItemStack(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER),
                    new ItemStack(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER)
                ));
            }
        );

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ItemRegistry.COXINHA))
                .title(Component.translatable("itemGroup.coxinhautilities.item_group"))
                .displayItems((ctx, entries) ->
                        entries.acceptAll(ItemGroupInit.ITEMS)
                )
        .build());
    }

    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(Registries.CREATIVE_MODE_TAB, CoxinhaUtilities.id("item_group"));

    public static final List<ItemStack> ITEMS = new ArrayList<>();
}