package me.luligabi.coxinhautilities.client.compat.rei;

import me.luligabi.coxinhautilities.client.compat.rei.drying.DryingDisplayCategory;
import me.luligabi.coxinhautilities.client.compat.rei.drying.DryingRecipeDisplay;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class ReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new DryingDisplayCategory());
        registry.addWorkstations(DRYING, EntryStacks.of(BlockRegistry.DRYING_RACK));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(DryingRecipe.class, DryingRecipeDisplay::new);
    }


    public static final CategoryIdentifier<DryingRecipeDisplay> DRYING = CategoryIdentifier.of(CoxinhaUtilities.MOD_ID, "drying");

}