package me.luligabi.coxinhautilities.common;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.OptionGroup;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModConfig implements ModMenuApi {

    // Ender Orchid
    public boolean canGenerateEnderOrchids = true;

    public int enderOrchidSpawnRate = 2;
    
    public boolean hasEnderOrchidStrictPlacement = true;

    public int enderOrchidRegularGrowthRate = 8;

    public int enderOrchidSpecialGrowthRate = 12;

    // Cardboard Box
    public boolean useCarrierBlacklist = true;


    public Screen createGui(Screen parent) {

        /*
         * Ender Orchid
         */
        Option<Integer> enderOrchidSpawnRate = Option.createBuilder(Integer.class)
                .name(Text.translatable("configOption.coxinhautilities.enderOrchidSpawnRate"))
                .tooltip(Text.translatable("configOption.coxinhautilities.enderOrchidSpawnRate.tooltip"))
                .binding(
                        2,
                        () -> this.enderOrchidSpawnRate,
                        newValue -> this.enderOrchidSpawnRate = newValue
                )
                .available(this.canGenerateEnderOrchids)
                .controller((intOption) -> new IntegerSliderController(intOption, 2, 20, 2))
                .build();

        Option<Boolean> canGenerateEnderOrchids = Option.createBuilder(Boolean.class)
                .name(Text.translatable("configOption.coxinhautilities.canGenerateEnderOrchids"))
                .tooltip(Text.translatable("configOption.coxinhautilities.canGenerateEnderOrchids.tooltip"))
                .binding(
                        true,
                        () -> this.canGenerateEnderOrchids,
                        newValue -> {
                            this.canGenerateEnderOrchids = newValue;
                            enderOrchidSpawnRate.setAvailable(newValue);
                        }
                )
                .controller((booleanOption) -> new BooleanController(booleanOption, BooleanController.YES_NO_FORMATTER, true))
                .build();

        Option<Integer> enderOrchidSpecialGrowthRate = Option.createBuilder(Integer.class)
                .name(Text.translatable("configOption.coxinhautilities.enderOrchidSpecialGrowthRate"))
                .tooltip(Text.translatable("configOption.coxinhautilities.enderOrchidSpecialGrowthRate.tooltip"))
                .binding(
                        12,
                        () -> this.enderOrchidSpecialGrowthRate,
                        newValue -> this.enderOrchidSpecialGrowthRate = newValue
                )
                .available(!this.hasEnderOrchidStrictPlacement)
                .controller((intOption) -> new IntegerSliderController(intOption, 2, 20, 2))
                .build();

        Option<Boolean> hasEnderOrchidStrictPlacement = Option.createBuilder(Boolean.class)
                .name(Text.translatable("configOption.coxinhautilities.hasEnderOrchidStrictPlacement"))
                .tooltip(Text.translatable("configOption.coxinhautilities.hasEnderOrchidStrictPlacement.tooltip"))
                .binding(
                        true,
                        () -> this.hasEnderOrchidStrictPlacement,
                        newValue -> {
                            this.hasEnderOrchidStrictPlacement = newValue;
                            enderOrchidSpecialGrowthRate.setAvailable(!newValue);
                        }

                )
                .controller((booleanOption) -> new BooleanController(booleanOption, BooleanController.YES_NO_FORMATTER, true))
                .build();

        Option<Integer> enderOrchidRegularGrowthRate = Option.createBuilder(Integer.class)
                .name(Text.translatable("configOption.coxinhautilities.enderOrchidRegularGrowthRate"))
                .tooltip(Text.translatable("configOption.coxinhautilities.enderOrchidRegularGrowthRate.tooltip"))
                .binding(
                        8,
                        () -> this.enderOrchidRegularGrowthRate,
                        newValue -> this.enderOrchidRegularGrowthRate = newValue
                )
                .controller((intOption) -> new IntegerSliderController(intOption, 2, 20, 2))
                .build();

        /*
         * Cardboard Box
         */
        Option<Boolean> useCarrierBlacklist = Option.createBuilder(Boolean.class)
                .name(Text.translatable("configOption.coxinhautilities.useCarrierBlacklist"))
                .tooltip(Text.translatable("configOption.coxinhautilities.useCarrierBlacklist.tooltip"))
                .binding(
                        true,
                        () -> this.useCarrierBlacklist,
                        newValue -> this.useCarrierBlacklist = newValue
                )
                .controller((booleanOption) -> new BooleanController(booleanOption, BooleanController.YES_NO_FORMATTER, true))
                .build();

        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("itemGroup.coxinhautilities.item_group"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("block.coxinhautilities.ender_orchid"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("block.coxinhautilities.ender_orchid"))

                                .option(canGenerateEnderOrchids)
                                .option(enderOrchidSpawnRate)
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
                .save(() -> CoxinhaUtilities.saveConfig(this))
                .build()
                .generateScreen(parent);
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::createGui;
    }
}