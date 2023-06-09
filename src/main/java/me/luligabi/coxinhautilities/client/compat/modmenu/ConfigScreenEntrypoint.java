package me.luligabi.coxinhautilities.client.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import me.luligabi.coxinhautilities.common.CoxinhaUtilities;
import me.luligabi.coxinhautilities.common.ModConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenEntrypoint implements ModMenuApi {

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return this::createConfigScreen;
    }

    private Screen createConfigScreen(Screen parent) {
        ModConfig config = CoxinhaUtilities.CONFIG;

        /*
         * Ender Orchid
         */
        Option<Boolean> canGenerateEnderOrchids = Option.<Boolean>createBuilder()
                .name(Text.translatable("configOption.coxinhautilities.canGenerateEnderOrchids"))
                .description(OptionDescription.of(Text.translatable("configOption.coxinhautilities.canGenerateEnderOrchids.tooltip")))
                .binding(
                        true,
                        () -> config.canGenerateEnderOrchids,
                        newValue -> config.canGenerateEnderOrchids = newValue
                )
                .controller(option -> BooleanControllerBuilder.create(option).yesNoFormatter().coloured(true))
                .build();

        Option<Integer> enderOrchidSpecialGrowthRate = Option.<Integer>createBuilder()
                .name(Text.translatable("configOption.coxinhautilities.enderOrchidSpecialGrowthRate"))
                .description(OptionDescription.of(Text.translatable("configOption.coxinhautilities.enderOrchidSpecialGrowthRate.tooltip")))
                .binding(
                        12,
                        () -> config.enderOrchidSpecialGrowthRate,
                        newValue -> config.enderOrchidSpecialGrowthRate = newValue
                )
                .available(!config.hasEnderOrchidStrictPlacement)
                .controller(option -> IntegerSliderControllerBuilder.create(option).range(2, 20).step(2))
                .build();

        Option<Boolean> hasEnderOrchidStrictPlacement = Option.<Boolean>createBuilder()
                .name(Text.translatable("configOption.coxinhautilities.hasEnderOrchidStrictPlacement"))
                .description(OptionDescription.of(Text.translatable("configOption.coxinhautilities.hasEnderOrchidStrictPlacement.tooltip")))
                .binding(
                        true,
                        () -> config.hasEnderOrchidStrictPlacement,
                        newValue -> {
                            config.hasEnderOrchidStrictPlacement = newValue;
                            enderOrchidSpecialGrowthRate.setAvailable(!newValue);
                        }

                )
                .controller(option -> BooleanControllerBuilder.create(option).yesNoFormatter().coloured(true))
                .build();

        Option<Integer> enderOrchidRegularGrowthRate = Option.<Integer>createBuilder()
                .name(Text.translatable("configOption.coxinhautilities.enderOrchidRegularGrowthRate"))
                .description(OptionDescription.of(Text.translatable("configOption.coxinhautilities.enderOrchidRegularGrowthRate.tooltip")))
                .binding(
                        8,
                        () -> config.enderOrchidRegularGrowthRate,
                        newValue -> config.enderOrchidRegularGrowthRate = newValue
                )
                .controller(option -> IntegerSliderControllerBuilder.create(option).range(2, 20).step(2))
                .build();

        /*
         * Cardboard Box
         */
        Option<Boolean> useCarrierBlacklist = Option.<Boolean>createBuilder()
                .name(Text.translatable("configOption.coxinhautilities.useCarrierBlacklist"))
                .description(OptionDescription.of(Text.translatable("configOption.coxinhautilities.useCarrierBlacklist.tooltip")))
                .binding(
                        true,
                        () -> config.useCarrierBlacklist,
                        newValue -> config.useCarrierBlacklist = newValue
                )
                .controller(option -> BooleanControllerBuilder.create(option).yesNoFormatter().coloured(true))
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("itemGroup.coxinhautilities.item_group"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("block.coxinhautilities.ender_orchid"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("block.coxinhautilities.ender_orchid"))

                                .option(canGenerateEnderOrchids)
                                .option(hasEnderOrchidStrictPlacement)
                                .option(enderOrchidRegularGrowthRate)
                                .option(enderOrchidSpecialGrowthRate)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("block.coxinhautilities.cardboard_box"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("block.coxinhautilities.cardboard_box"))

                                .option(useCarrierBlacklist)
                                .build())
                        .build())
                .save(() -> CoxinhaUtilities.saveConfig(config))
                .build()
                .generateScreen(parent);
    }

}