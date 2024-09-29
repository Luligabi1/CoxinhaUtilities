package me.luligabi.coxinhautilities.common.item;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.battery.BakedPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PoisonousPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PotatoBatteryItem;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
        Registry.register(Registries.ITEM, CoxinhaUtilities.id(id), item);
        if(!isHidden) {
            ItemGroupInit.ITEMS.add(new ItemStack(item));
        }
    }

    public static final Item COXINHA = new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.8F).build())) {
        
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) { 
            tooltip.add(Text.translatable("tooltip.coxinhautilities.coxinha").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC)); 
        }
    };

    public static final Item CURSED_COXINHA = new Item(new Item.Settings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().nutrition(10).saturationModifier(1.2F).build())) {
        
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) { 
            tooltip.add(Text.translatable("tooltip.kibe.lore.cursed_kibe"));
        }
    };

    public static final Item GOLDEN_COXINHA = new Item(new Item.Settings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().nutrition(8).saturationModifier(1.2F).build())) {
        
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.kibe.lore.golden_kibe")); 
        }
    };

    public static final Item DIAMOND_COXINHA = new Item(new Item.Settings().rarity(Rarity.RARE).food(new FoodComponent.Builder().nutrition(16).saturationModifier(1.4F).build())) {
        
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.kibe.lore.diamond_kibe")); 
        }
    };

    public static final Item POTATO_BATTERY = new PotatoBatteryItem(new Item.Settings().maxCount(1));

    public static final Item BAKED_POTATO_BATTERY = new BakedPotatoBatteryItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

    public static final Item POISONOUS_POTATO_BATTERY = new PoisonousPotatoBatteryItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE));


    public static final Item ENDER_ORCHID_SEEDS = new AliasedBlockItem(BlockRegistry.ENDER_ORCHID, new Item.Settings());

}