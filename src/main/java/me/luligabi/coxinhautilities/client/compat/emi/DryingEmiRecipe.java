package me.luligabi.coxinhautilities.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryingEmiRecipe implements EmiRecipe {

    public DryingEmiRecipe(DryingRecipe recipe) {
        this.recipe = recipe;
        this.input = EmiIngredient.of(recipe.getIngredient());
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CoxinhaEmiPlugin.DRYING_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return recipe.getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 82;
    }

    @Override
    public int getDisplayHeight() {
        return 26;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(24, 5, recipe.getDryingTime() * 50).tooltip((mx, my) ->
                List.of(TooltipComponent.of(Text.translatable("emi.cooking.time", recipe.getDryingTime() / 20F).asOrderedText()))
        );

        widgets.addSlot(input, 0, 4);
        widgets.addSlot(output, 56, 0).output(true).recipeContext(this);
    }

    private final DryingRecipe recipe;
    private final EmiIngredient input;
    private final EmiStack output;
}
