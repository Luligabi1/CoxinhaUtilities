package me.luligabi.coxinhautilities.common.recipe.drying;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DryingRecipeSerializer implements RecipeSerializer<DryingRecipe> {

    public static final MapCodec<DryingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(DryingRecipe::getIngredient),
            Codec.INT.optionalFieldOf("dryingTime", 20).forGetter(DryingRecipe::getDryingTime),
            ItemStack.CODEC.fieldOf("result").forGetter(DryingRecipe::getOutput))
        .apply(instance, DryingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> PACKET_CODEC = StreamCodec.of(
        DryingRecipeSerializer::toNetwork,
        DryingRecipeSerializer::fromNetwork
    );

    private static DryingRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
        int dryingTime = buf.readInt();
        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);

        return new DryingRecipe(
            ingredient,
            dryingTime,
            result
        );
    }

    private static void toNetwork(RegistryFriendlyByteBuf buf, DryingRecipe recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getIngredient());
        buf.writeInt(recipe.getDryingTime());
        ItemStack.STREAM_CODEC.encode(buf, recipe.getOutput());
    }

    @Override
    public MapCodec<DryingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, DryingRecipe> streamCodec() {
        return PACKET_CODEC;
    }

}