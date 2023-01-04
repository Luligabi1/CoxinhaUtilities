package me.luligabi.coxinhautilities.common.recipe.drying;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class DryingRecipeSerializer implements RecipeSerializer<DryingRecipe> {

    private DryingRecipeSerializer() {
    }

    public static final DryingRecipeSerializer INSTANCE = new DryingRecipeSerializer();

    public static final Identifier ID = new Identifier(CoxinhaUtilities.MOD_ID, "drying");

    @Override // Turns json into Recipe
    public DryingRecipe read(Identifier recipeId, JsonObject json) {
        DryingRecipeJsonFormat recipeJson = new Gson().fromJson(json, DryingRecipeJsonFormat.class);
        if (recipeJson.ingredient == null || recipeJson.outputItem == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        if(recipeJson.dryingTime <= 0) recipeJson.dryingTime = 20;

        Ingredient input = Ingredient.fromJson(recipeJson.ingredient);

        Item outputItem = Registries.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));

        int dryingTime = recipeJson.dryingTime;

        return new DryingRecipe(input, new ItemStack(outputItem), dryingTime, recipeId);
    }

    @Override // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf packetData, DryingRecipe recipe) {
        recipe.getIngredient().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
        packetData.writeInt(recipe.getDryingTime());
    }

    @Override // Turns PacketByteBuf into Recipe
    public DryingRecipe read(Identifier recipeId, PacketByteBuf packetData) {
        Ingredient input = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        int dryingTime = packetData.readInt();
        return new DryingRecipe(input, output, dryingTime, recipeId);
    }

}