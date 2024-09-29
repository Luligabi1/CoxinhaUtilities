package me.luligabi.coxinhautilities.common.misc.lootfunction;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlock;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

import java.util.List;

public class TankCopyDataLootFunction extends ConditionalLootFunction {

    public TankCopyDataLootFunction(List<LootCondition> conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        BlockEntity blockEntity = context.requireParameter(LootContextParameters.BLOCK_ENTITY);
        if(stack.getItem() instanceof PortableTankBlockItem) {
            ((PortableTankBlock) ((PortableTankBlockItem) stack.getItem()).getBlock()).saveNbtToStack(blockEntity, stack);
        }
        return stack;
    }

    @Override
    public LootFunctionType<TankCopyDataLootFunction> getType() {
        return LootFunctionRegistry.TANK_COPY_DATA;
    }

    public static final MapCodec<TankCopyDataLootFunction> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return addConditionsField(instance).apply(instance, TankCopyDataLootFunction::new);
    });

}