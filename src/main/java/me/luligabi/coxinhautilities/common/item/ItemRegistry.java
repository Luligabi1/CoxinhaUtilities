package me.luligabi.coxinhautilities.common.item;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.battery.BakedPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PoisonousPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PotatoBatteryItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemRegistry {

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "coxinha"), COXINHA);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "cursed_coxinha"), CURSED_COXINHA);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "golden_coxinha"), GOLDEN_COXINHA);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "diamond_coxinha"), DIAMOND_COXINHA);

        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "potato_battery"), POTATO_BATTERY);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "baked_potato_battery"), BAKED_POTATO_BATTERY);
        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "poisonous_potato_battery"), POISONOUS_POTATO_BATTERY);

        Registry.register(Registry.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, "ender_orchid_seeds"), ENDER_ORCHID_SEEDS);
    }

    public static final Item COXINHA = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build()).group(CoxinhaUtilities.ITEM_GROUP)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(new TranslatableText("tooltip.coxinhautilities.coxinha").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC)); }
    };

    public static final Item CURSED_COXINHA = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().hunger(10).saturationModifier(1.2F).meat().build()).group(CoxinhaUtilities.ITEM_GROUP)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(new TranslatableText("tooltip.kibe.lore.cursed_kibe")); }
    };

    public static final Item GOLDEN_COXINHA = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().hunger(8).saturationModifier(1.2F).meat().build()).group(CoxinhaUtilities.ITEM_GROUP)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(new TranslatableText("tooltip.kibe.lore.golden_kibe")); }
    };

    public static final Item DIAMOND_COXINHA = new Item(new FabricItemSettings().rarity(Rarity.RARE).food(new FoodComponent.Builder().hunger(16).saturationModifier(1.4F).meat().build()).group(CoxinhaUtilities.ITEM_GROUP)) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(new TranslatableText("tooltip.kibe.lore.diamond_kibe")); }
    };

    public static final Item POTATO_BATTERY = new PotatoBatteryItem(new FabricItemSettings().maxCount(1).group(CoxinhaUtilities.ITEM_GROUP));

    public static final Item BAKED_POTATO_BATTERY = new BakedPotatoBatteryItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON).group(CoxinhaUtilities.ITEM_GROUP));

    public static final Item POISONOUS_POTATO_BATTERY = new PoisonousPotatoBatteryItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(CoxinhaUtilities.ITEM_GROUP));


    public static final Item ENDER_ORCHID_SEEDS = new AliasedBlockItem(BlockRegistry.ENDER_ORCHID, new FabricItemSettings().group(CoxinhaUtilities.ITEM_GROUP));

}