package me.luligabi.coxinhautilities.common.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;

public class ConfigScreenEntrypoint implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return CoxinhaUtilities.CONFIG::createGui;
    }
}
