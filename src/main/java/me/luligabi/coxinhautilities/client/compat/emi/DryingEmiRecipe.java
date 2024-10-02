package me.luligabi.coxinhautilities.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.luligabi.coxinhautilities.common.recipe.drying.DryingRecipe;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryingEmiRecipe implements EmiRecipe {

    public DryingEmiRecipe(RecipeHolder<DryingRecipe> entry) {
        this.entry = entry;
        this.input = EmiIngredient.of(entry.value().getIngredient());
        this.output = EmiStack.of(entry.value().getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CoxinhaEmiPlugin.DRYING_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return entry.id();
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
        widgets.addFillingArrow(24, 5, entry.value().getDryingTime() * 50).tooltip((mx, my) ->
                List.of(ClientTooltipComponent.create(Component.translatable("emi.cooking.time", entry.value().getDryingTime() / 20F).getVisualOrderText()))
        );

        widgets.addSlot(input, 0, 4);
        widgets.addSlot(output, 56, 0).large(true).recipeContext(this);
    }

    private final RecipeHolder<DryingRecipe> entry;
    private final EmiIngredient input;
    private final EmiStack output;
}