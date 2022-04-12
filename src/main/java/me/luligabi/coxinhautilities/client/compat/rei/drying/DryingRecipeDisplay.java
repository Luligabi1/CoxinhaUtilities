package me.luligabi.coxinhautilities.client.compat.rei.drying;

import me.luligabi.coxinhautilities.client.compat.rei.ReiPlugin;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;
import java.util.List;

public class DryingRecipeDisplay implements Display {

    protected DryingRecipe recipe;


    protected List<EntryIngredient> input;
    protected List<EntryIngredient> output;

    protected int dryingTime;

    public DryingRecipeDisplay(DryingRecipe recipe) {
        this.recipe = recipe;

        this.input = EntryIngredients.ofIngredients(List.of(recipe.getIngredient()));
        this.output = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
        this.dryingTime = recipe.getDryingTime();
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
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ReiPlugin.DRYING;
    }

}