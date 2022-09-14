package me.luligabi.coxinhautilities.common.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
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
        tooltip.add(Text.empty());
        wittyComments().forEach(text -> tooltip.add(text.copyContentOnly().formatted(Formatting.BLUE, Formatting.ITALIC)));
    }

    List<Text> wittyComments();

}