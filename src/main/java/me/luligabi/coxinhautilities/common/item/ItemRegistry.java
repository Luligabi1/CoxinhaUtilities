package me.luligabi.coxinhautilities.common.item;

import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.item.battery.BakedPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PoisonousPotatoBatteryItem;
import me.luligabi.coxinhautilities.common.item.battery.PotatoBatteryItem;
import me.luligabi.coxinhautilities.common.misc.ItemGroupInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
        Registry.register(Registries.ITEM, new Identifier(CoxinhaUtilities.MOD_ID, id), item);
        if(!isHidden) {
            ItemGroupInit.ITEMS.add(new ItemStack(item));
        }
    }

    public static final Item COXINHA = new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).meat().build())) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(Text.translatable("tooltip.coxinhautilities.coxinha").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC)); }
    };

    public static final Item CURSED_COXINHA = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().hunger(10).saturationModifier(1.2F).meat().build())) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(Text.translatable("tooltip.kibe.lore.cursed_kibe")); }
    };

    public static final Item GOLDEN_COXINHA = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON).food(new FoodComponent.Builder().hunger(8).saturationModifier(1.2F).meat().build())) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(Text.translatable("tooltip.kibe.lore.golden_kibe")); }
    };

    public static final Item DIAMOND_COXINHA = new Item(new FabricItemSettings().rarity(Rarity.RARE).food(new FoodComponent.Builder().hunger(16).saturationModifier(1.4F).meat().build())) {
        @Override
        public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) { tooltip.add(Text.translatable("tooltip.kibe.lore.diamond_kibe")); }
    };

    public static final Item POTATO_BATTERY = new PotatoBatteryItem(new FabricItemSettings().maxCount(1));

    public static final Item BAKED_POTATO_BATTERY = new BakedPotatoBatteryItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON));

    public static final Item POISONOUS_POTATO_BATTERY = new PoisonousPotatoBatteryItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));


    public static final Item ENDER_ORCHID_SEEDS = new AliasedBlockItem(BlockRegistry.ENDER_ORCHID, new FabricItemSettings());

}