package me.luligabi.coxinhautilities.common.misc;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemGroupInit {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, CoxinhaUtilities.MOD_ID);

    public static final Supplier<CreativeModeTab> ITEM_GROUP = CREATIVE_MODE_TABS.register(
        "item_group",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ItemRegistry.COXINHA.get()))
            .title(Component.translatable("itemGroup.coxinhautilities.item_group"))
            .displayItems((params, output) -> {
                output.accept(ItemRegistry.COXINHA.get());
                output.accept(ItemRegistry.CURSED_COXINHA.get());
                output.accept(ItemRegistry.GOLDEN_COXINHA.get());
                output.accept(ItemRegistry.DIAMOND_COXINHA.get());

                output.accept(ItemRegistry.POTATO_BATTERY.get());
                output.accept(ItemRegistry.BAKED_POTATO_BATTERY.get());
                output.accept(ItemRegistry.POISONOUS_POTATO_BATTERY.get());

                output.accept(ItemRegistry.ENDER_ORCHID_SEEDS.get());

                output.accept(BlockRegistry.WOODEN_HOPPER.get());

                output.accept(BlockRegistry.PORTABLE_TANK_MK1.get());
                output.accept(BlockRegistry.PORTABLE_TANK_MK2.get());
                output.accept(BlockRegistry.PORTABLE_TANK_MK3.get());
                output.accept(BlockRegistry.PORTABLE_TANK_MK4.get());
                output.accept(BlockRegistry.PORTABLE_TANK_MK5.get());

                output.accept(BlockRegistry.GRANNYS_SINK.get());
                output.accept(BlockRegistry.FLUID_TRASH_CAN.get());
                output.accept(BlockRegistry.ENERGY_TRASH_CAN.get());

                output.accept(BlockRegistry.DRYING_RACK.get());

                output.accept(BlockRegistry.CARDBOARD_BOX.get());

                output.accept(BlockRegistry.AQUATIC_TORCH.get());

                output.accept(BlockRegistry.COPPER_LADDER.get());
                output.accept(BlockRegistry.EXPOSED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WEATHERED_COPPER_LADDER.get());
                output.accept(BlockRegistry.OXIDIZED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get());
                output.accept(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get());

                output.accept(BlockRegistry.LAVA_SPONGE.get());
                output.accept(BlockRegistry.WET_LAVA_SPONGE.get());

                output.accept(ItemRegistry.COXINHA_BANNER_PATTERN.get());
            })
            .build()
    );

    @SubscribeEvent
    public static void onCreativeModeTabBuild(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.insertAfter(new ItemStack(Items.COOKED_CHICKEN), new ItemStack(ItemRegistry.COXINHA.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ItemRegistry.COXINHA.get()), new ItemStack(ItemRegistry.CURSED_COXINHA.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ItemRegistry.CURSED_COXINHA.get()), new ItemStack(ItemRegistry.GOLDEN_COXINHA.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(ItemRegistry.GOLDEN_COXINHA.get()), new ItemStack(ItemRegistry.DIAMOND_COXINHA.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.insertBefore(new ItemStack(Items.CHORUS_PLANT), new ItemStack(ItemRegistry.ENDER_ORCHID_SEEDS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(Items.WET_SPONGE), new ItemStack(BlockRegistry.LAVA_SPONGE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.LAVA_SPONGE.get()), new ItemStack(BlockRegistry.WET_LAVA_SPONGE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.insertBefore(new ItemStack(Items.HOPPER), new ItemStack(BlockRegistry.WOODEN_HOPPER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.insertBefore(new ItemStack(Items.SOUL_TORCH), new ItemStack(BlockRegistry.AQUATIC_TORCH.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(new ItemStack(Blocks.LADDER), new ItemStack(BlockRegistry.COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.COPPER_LADDER.get()), new ItemStack(BlockRegistry.EXPOSED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.EXPOSED_COPPER_LADDER.get()), new ItemStack(BlockRegistry.WEATHERED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.WEATHERED_COPPER_LADDER.get()), new ItemStack(BlockRegistry.OXIDIZED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(new ItemStack(BlockRegistry.OXIDIZED_COPPER_LADDER.get()), new ItemStack(BlockRegistry.WAXED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.WAXED_COPPER_LADDER.get()), new ItemStack(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.WAXED_EXPOSED_COPPER_LADDER.get()), new ItemStack(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(new ItemStack(BlockRegistry.WAXED_WEATHERED_COPPER_LADDER.get()), new ItemStack(BlockRegistry.WAXED_OXIDIZED_COPPER_LADDER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        ITEMS.add(new ItemStack(ItemRegistry.COXINHA_BANNER_PATTERN.get()));
    }


    public static final List<ItemStack> ITEMS = new ArrayList<>();
}