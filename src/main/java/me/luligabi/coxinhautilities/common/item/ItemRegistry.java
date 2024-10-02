package me.luligabi.coxinhautilities.common.item;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.battery.BakedPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PoisonousPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PotatoBatteryItem;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

import java.util.List;

// FIXME change tooltips that rely on Kibe
public class ItemRegistry {

    public static void init() {
        initItem("coxinha", COXINHA);
        initItem("cursed_coxinha", CURSED_COXINHA);
        initItem("golden_coxinha", GOLDEN_COXINHA);
        initItem("diamond_coxinha", DIAMOND_COXINHA);

        initItem("potato_battery", POTATO_BATTERY);
        initItem("baked_potato_battery", BAKED_POTATO_BATTERY);
        initItem("poisonous_potato_battery", POISONOUS_POTATO_BATTERY);

        initItem("ender_orchid_seeds", ENDER_ORCHID_SEEDS);
    }

    private static void initItem(String id, Item item) {
        initItem(id, item, false);
    }

    @SuppressWarnings("SameParameterValue")
    private static void initItem(String id, Item item, boolean isHidden) {
        Registry.register(BuiltInRegistries.ITEM, CoxinhaUtilities.id(id), item);
        if(!isHidden) {
            ItemGroupInit.ITEMS.add(new ItemStack(item));
        }
    }

    public static final Item COXINHA = new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.8F).build())) {
        
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) { 
            tooltip.add(Component.translatable("tooltip.coxinhautilities.coxinha").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC)); 
        }
    };

    public static final Item CURSED_COXINHA = new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder().nutrition(10).saturationModifier(1.2F).build())) {
        
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) { 
            tooltip.add(Component.translatable("tooltip.coxinhautilities.cursed_coxinha"));
        }
    };

    public static final Item GOLDEN_COXINHA = new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.2F).build()));

    public static final Item DIAMOND_COXINHA = new Item(new Item.Properties().rarity(Rarity.RARE).food(new FoodProperties.Builder().nutrition(16).saturationModifier(1.4F).build()));

    public static final Item POTATO_BATTERY = new PotatoBatteryItem(new Item.Properties().stacksTo(1));

    public static final Item BAKED_POTATO_BATTERY = new BakedPotatoBatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));

    public static final Item POISONOUS_POTATO_BATTERY = new PoisonousPotatoBatteryItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));


    public static final Item ENDER_ORCHID_SEEDS = new ItemNameBlockItem(BlockRegistry.ENDER_ORCHID, new Item.Properties());

}