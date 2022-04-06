package me.luligabi.coxinhautilities.client.compat.rei.drying;

import com.google.common.collect.Lists;
import me.luligabi.coxinhautilities.client.compat.rei.ReiPlugin;
import me.luligabi.coxinhautilities.common.block.BlockRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class DryingDisplayCategory implements DisplayCategory<DryingRecipeDisplay> {

    @Override
    public List<Widget> setupDisplay(DryingRecipeDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        widgets.add(Widgets.createLabel(new Point(bounds.x + bounds.width - 5, bounds.y + 5),
                new TranslatableText("category.rei.campfire.time", display.getDryingTime() / 20)).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        return widgets;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(BlockRegistry.DRYING_RACK);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("block.coxinhautilities.drying_rack");
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }

    @Override
    public CategoryIdentifier<? extends DryingRecipeDisplay> getCategoryIdentifier() {
        return ReiPlugin.DRYING;
    }
}
