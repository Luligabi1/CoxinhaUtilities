package me.luligabi.coxinhautilities.common.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.List;

/*
 * Used to easily add a hidden, probably witty, comment on an
 * item's tooltip.
 *
 * The comment is only shown when the user holds the SHIFT key.
 */
public interface IWittyComment {

    default void addWittyComment(List<Text> tooltip) {
        if(!Screen.hasShiftDown()) return;
        tooltip.add(new LiteralText(""));
        wittyComments().forEach(text -> tooltip.add(text.formatted(Formatting.BLUE, Formatting.ITALIC)));
    }

    List<TranslatableText> wittyComments();

}