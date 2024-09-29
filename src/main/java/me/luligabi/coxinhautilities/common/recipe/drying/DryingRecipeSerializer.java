package me.luligabi.coxinhautilities.common.recipe.drying;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;

public class DryingRecipeSerializer implements RecipeSerializer<DryingRecipe> {

    public static final MapCodec<DryingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(DryingRecipe::getIngredient),
            Codec.INT.optionalFieldOf("dryingTime", 20).forGetter(DryingRecipe::getDryingTime),
            ItemStack.CODEC.fieldOf("result").forGetter(DryingRecipe::getOutput))
        .apply(instance, DryingRecipe::new));

    public static final PacketCodec<RegistryByteBuf, DryingRecipe> PACKET_CODEC = PacketCodec.ofStatic(
        DryingRecipeSerializer::toNetwork,
        DryingRecipeSerializer::fromNetwork
    );

    private static DryingRecipe fromNetwork(RegistryByteBuf buf) {
        Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
        int dryingTime = buf.readInt();
        ItemStack result = ItemStack.PACKET_CODEC.decode(buf);

        return new DryingRecipe(
            ingredient,
            dryingTime,
            result
        );
    }

    private static void toNetwork(RegistryByteBuf buf, DryingRecipe recipe) {
        Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient());
        buf.writeInt(recipe.getDryingTime());
        ItemStack.PACKET_CODEC.encode(buf, recipe.getOutput());
    }

    @Override
    public MapCodec<DryingRecipe> codec() {
        return CODEC;
    }

    @Override
    public PacketCodec<RegistryByteBuf, DryingRecipe> packetCodec() {
        return PACKET_CODEC;
    }

}