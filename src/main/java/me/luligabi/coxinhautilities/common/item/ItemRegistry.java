package me.luligabi.coxinhautilities.common.item;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.battery.BakedPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PoisonousPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PotatoBatteryItem;
import me.luligabi.coxinhautilities.common.misc.TagRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CoxinhaUtilities.MOD_ID);

    public static final Supplier<Item> COXINHA = ITEMS.register(
        "coxinha",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build())) {

            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
                tooltip.add(Component.translatable("tooltip.coxinhautilities.coxinha").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
            }
        }
    );

    public static final Supplier<Item> CURSED_COXINHA = ITEMS.register(
        "cursed_coxinha",
        () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder().nutrition(10).saturationModifier(1.2F).build())) {

            @Override
            public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
                tooltip.add(Component.translatable("tooltip.coxinhautilities.cursed_coxinha"));
            }
        }
    );

    public static final Supplier<Item> GOLDEN_COXINHA = ITEMS.register(
        "golden_coxinha",
        () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.2F).build()))
    );

    public static final Supplier<Item> DIAMOND_COXINHA = ITEMS.register(
        "diamond_coxinha",
        () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(new FoodProperties.Builder().nutrition(16).saturationModifier(1.4F).build()))
    );

    public static final Supplier<PotatoBatteryItem> POTATO_BATTERY = ITEMS.register(
        "potato_battery",
        () -> new PotatoBatteryItem(new Item.Properties().stacksTo(1))
    );

    public static final Supplier<PotatoBatteryItem> BAKED_POTATO_BATTERY = ITEMS.register(
        "baked_potato_battery",
        () -> new BakedPotatoBatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
    );

    public static final Supplier<PotatoBatteryItem> POISONOUS_POTATO_BATTERY = ITEMS.register(
        "poisonous_potato_battery",
        () -> new PoisonousPotatoBatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE))
    );

    public static final Supplier<Item> ENDER_ORCHID_SEEDS = ITEMS.register(
        "ender_orchid_seeds",
        () -> new ItemNameBlockItem(BlockRegistry.ENDER_ORCHID.get(), new Item.Properties())
    );

    public static final Supplier<Item> COXINHA_BANNER_PATTERN = ITEMS.register(
        "coxinha_banner_pattern",
        () -> new BannerPatternItem(TagRegistry.PATTERN_ITEM_COXINHA, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC))
    );

}