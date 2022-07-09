package me.luligabi.coxinhautilities.common.lootfunction;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlock;
import me.luligabi.coxinhautilities.common.block.tank.PortableTankBlockItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

public class TankCopyDataLootFunction extends ConditionalLootFunction {

    public TankCopyDataLootFunction(LootCondition[] conditions) { super(conditions); }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        BlockEntity blockEntity = context.requireParameter(LootContextParameters.BLOCK_ENTITY);
        if(stack.getItem() instanceof PortableTankBlockItem) {
            ((PortableTankBlock) ((PortableTankBlockItem) stack.getItem()).getBlock()).saveTankNbtToStack(blockEntity, stack);
        }
        return stack;
    }


    @Override
    public LootFunctionType getType() { return CoxinhaUtilities.TANK_COPY_DATA; }

    public static class TankCopyDataLootFunctionSerializer extends ConditionalLootFunction.Serializer<TankCopyDataLootFunction> {

        @Override
        public TankCopyDataLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return new TankCopyDataLootFunction(conditions);
        }

    }

}