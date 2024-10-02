package me.luligabi.coxinhautilities.client.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipeType;
import net.minecraft.network.chat.Component;

public class CoxinhaEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(DRYING_CATEGORY);
        registry.addWorkstation(DRYING_CATEGORY, EmiStack.of(BlockRegistry.DRYING_RACK));
        registry.getRecipeManager()
                .getAllRecipesFor(DryingRecipeType.INSTANCE)
                .stream()
                .map(DryingEmiRecipe::new)
                .forEach(registry::addRecipe);
    }

    public static final EmiRecipeCategory DRYING_CATEGORY = new EmiRecipeCategory(
            CoxinhaUtilities.id("drying"),
            EmiStack.of(BlockRegistry.DRYING_RACK)
    ) {
        @Override
        public Component getName() {
                    return Component.translatable("block.coxinhautilities.drying_rack");
                }
    };

}