package me.luligabi.coxinhautilities.common.misc.lootfunction;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlock;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlockItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

import java.util.List;

public class CardboardBoxCopyDataLootFunction extends ConditionalLootFunction {

    public CardboardBoxCopyDataLootFunction(List<LootCondition> conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        BlockEntity blockEntity = context.requireParameter(LootContextParameters.BLOCK_ENTITY);
        if(stack.getItem() instanceof CardboardBoxBlockItem) {
            ((CardboardBoxBlock) ((CardboardBoxBlockItem) stack.getItem()).getBlock()).saveNbtToStack(blockEntity, stack);
        }
        return stack;
    }

    @Override
    public LootFunctionType<CardboardBoxCopyDataLootFunction> getType() {
        return LootFunctionRegistry.CARDBOARD_BOX_COPY_DATA;
    }

    public static final MapCodec<CardboardBoxCopyDataLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return addConditionsField(instance).apply(instance, CardboardBoxCopyDataLootFunction::new);
    });

}