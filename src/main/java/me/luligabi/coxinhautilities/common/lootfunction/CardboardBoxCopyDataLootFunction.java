package me.luligabi.coxinhautilities.common.lootfunction;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlock;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlockItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

public class CardboardBoxCopyDataLootFunction extends ConditionalLootFunction {

    public CardboardBoxCopyDataLootFunction(LootCondition[] conditions) { super(conditions); }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        BlockEntity blockEntity = context.requireParameter(LootContextParameters.BLOCK_ENTITY);
        if(stack.getItem() instanceof CardboardBoxBlockItem) {
            ((CardboardBoxBlock) ((CardboardBoxBlockItem) stack.getItem()).getBlock()).saveTankNbtToStack(blockEntity, stack);
        }
        return stack;
    }

    @Override
    public LootFunctionType getType() { return LootFunctionRegistry.CARDBOARD_BOX_COPY_DATA; }

    public static class CardboardBoxCopyDataLootFunctionSerializer extends Serializer<CardboardBoxCopyDataLootFunction> {

        @Override
        public CardboardBoxCopyDataLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return new CardboardBoxCopyDataLootFunction(conditions);
        }

    }

}