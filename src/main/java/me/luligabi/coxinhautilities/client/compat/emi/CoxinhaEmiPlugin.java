package me.luligabi.coxinhautilities.client.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CoxinhaEmiPlugin implements EmiPlugin {
    public static final EmiRecipeCategory DRYING_CATEGORY =
            new EmiRecipeCategory(new Identifier(CoxinhaUtilities.MOD_ID, "drying"), EmiStack.of(BlockRegistry.DRYING_RACK)) {
                @Override
                public Text getName() {
                    return Text.translatable("block.coxinhautilities.drying_rack");
                }
            };

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(DRYING_CATEGORY);
        registry.addWorkstation(DRYING_CATEGORY, EmiStack.of(BlockRegistry.DRYING_RACK));
        registry.getRecipeManager()
                .listAllOfType(DryingRecipe.Type.INSTANCE)
                .stream()
                .map(DryingEmiRecipe::new)
                .forEach(registry::addRecipe);
    }
}
