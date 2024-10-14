package me.luligabi.coxinhautilities.client.compat.rei.drying;

import me.luligabi.coxinhautilities.client.compat.rei.CoxinhaReiPlugin;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DryingRecipeDisplay implements Display {

    protected RecipeHolder<DryingRecipe>  recipe;


    protected List<EntryIngredient> input;
    protected List<EntryIngredient> output;

    protected int dryingTime;

    public DryingRecipeDisplay(RecipeHolder<DryingRecipe> recipe) {
        this.recipe = recipe;

        this.input = EntryIngredients.ofIngredients(List.of(recipe.value().getIngredient()));
        this.output = Collections.singletonList(EntryIngredients.of(recipe.value().getOutput()));
        this.dryingTime = recipe.value().getDryingTime();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return input;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return output;
    }

    public int getDryingTime() {
        return dryingTime;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.of(recipe.id());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CoxinhaReiPlugin.DRYING;
    }

}