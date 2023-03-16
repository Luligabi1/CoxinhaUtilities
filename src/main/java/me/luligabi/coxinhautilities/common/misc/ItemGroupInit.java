package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class ItemGroupInit {

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries ->
                entries.addAfter(new ItemStack(Items.COOKED_CHICKEN), List.of(
                        new ItemStack(ItemRegistry.COXINHA),
                        new ItemStack(ItemRegistry.CURSED_COXINHA),
                        new ItemStack(ItemRegistry.GOLDEN_COXINHA),
                        new ItemStack(ItemRegistry.DIAMOND_COXINHA)
                ))
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
                entries.addBefore(new ItemStack(Items.CHORUS_PLANT), new ItemStack(ItemRegistry.ENDER_ORCHID_SEEDS));
                entries.addAfter(new ItemStack(Items.WET_SPONGE), List.of(
                    new ItemStack(BlockRegistry.LAVA_SPONGE),
                    new ItemStack(BlockRegistry.WET_LAVA_SPONGE)
                ));
            }
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries ->
                entries.addBefore(new ItemStack(Items.HOPPER), new ItemStack(BlockRegistry.WOODEN_HOPPER))
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
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
    }

    @SuppressWarnings("unused")
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(CoxinhaUtilities.MOD_ID, "item_group"))
            .icon(() -> new ItemStack(ItemRegistry.COXINHA))
            .displayName(Text.translatable("itemGroup.coxinhautilities.item_group"))
            .entries((ctx, entries) ->
                    entries.addAll(ItemGroupInit.ITEMS)
            )
            .build();

    public static final List<ItemStack> ITEMS = new ArrayList<>();
}
