package me.luligabi.coxinhautilities.client.compat.rei.widget;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;

import java.util.List;
import java.util.function.Consumer;

public class TooltippedArrow extends Arrow {

    private final Arrow arrow;
    private  final Consumer<Tooltip> tooltipConsumer;

    public TooltippedArrow(Point currentPoint, Consumer<Tooltip> tooltipConsumer) {
        this.arrow = Widgets.createArrow(currentPoint);
        this.tooltipConsumer = tooltipConsumer;
    }

    @Override
    public Rectangle getBounds() {
        return arrow.getBounds();
    }

    @Override
    public List<? extends Element> children() {
        return arrow.children();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        arrow.render(ctx, mouseX, mouseY, delta);
        final Point mousePoint = new Point(mouseX, mouseY);
        if (containsMouse(mousePoint)) getTooltip(TooltipContext.of(mousePoint)).queue();
    }

    @Override
    public double getAnimationDuration() {
        return arrow.getAnimationDuration();
    }

    @Override
    public void setAnimationDuration(double animationDurationMS) {
        arrow.setAnimationDuration(animationDurationMS);
    }

    @Override
    public Tooltip getTooltip(TooltipContext context) {
        Tooltip tooltip = super.getTooltip(context);
        if (tooltip == null) tooltip = Tooltip.create(context.getPoint());
        tooltipConsumer.accept(tooltip);
        return tooltip;
    }

}