package me.luligabi.coxinhautilities.common.misc.lootfunction;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlock;
import me.luligabi.coxinhautilities.common.block.cardboardbox.CardboardBoxBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class CardboardBoxCopyDataLootFunction extends LootItemConditionalFunction {

    public CardboardBoxCopyDataLootFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        BlockEntity blockEntity = context.getParam(LootContextParams.BLOCK_ENTITY);
        if(stack.getItem() instanceof CardboardBoxBlockItem) {
            ((CardboardBoxBlock) ((CardboardBoxBlockItem) stack.getItem()).getBlock()).saveNbtToStack(blockEntity, stack);
        }
        return stack;
    }

    @Override
    public LootItemFunctionType<CardboardBoxCopyDataLootFunction> getType() {
        return LootFunctionRegistry.CARDBOARD_BOX_COPY_DATA.get();
    }

    public static final MapCodec<CardboardBoxCopyDataLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return commonFields(instance).apply(instance, CardboardBoxCopyDataLootFunction::new);
    });

}