package me.luligabi.coxinhautilities.common.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

/*
 * Used to easily add a hidden, probably witty, comment on an
 * item's tooltip.
 *
 * The comment is only shown when the user holds the SHIFT key.
 */
public interface IWittyComment {

    default void addWittyComment(List<Component> tooltip) {
        if(!Screen.hasShiftDown()) return;
        tooltip.add(Component.empty());
        wittyComments().forEach(text -> tooltip.add(text.plainCopy().withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC)));
    }

    List<Component> wittyComments();

}