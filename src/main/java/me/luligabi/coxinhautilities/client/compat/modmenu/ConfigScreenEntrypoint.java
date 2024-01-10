package me.luligabi.coxinhautilities.client.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.luligabi.coxinhautilities.common.ModConfig;
import net.minecraft.client.gui.screen.Screen;

public class ConfigScreenEntrypoint implements ModMenuApi {

    private Screen screen;

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return parent -> {
            if(screen == null) {
                screen = ModConfig.HANDLER.generateGui().generateScreen(parent);
            }
            return screen;
        };
    }

}