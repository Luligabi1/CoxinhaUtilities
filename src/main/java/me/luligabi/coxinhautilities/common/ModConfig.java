package me.luligabi.coxinhautilities.common;

import blue.endless.jankson.annotation.Nullable;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

public class ModConfig implements Config {

    // Ender Orchid
    @Comment(value = "Whether or not Ender Orchids can generate in The End.")
    public boolean canGenerateEnderOrchids = true;

    @Comment(value = "Ender Orchid's spawn ratio; Increase to generate more Ender Orchids per chunk.")
    public int enderOrchidSpawnRatio = 2;
    
    @Comment(value = "Whether or not Ender Orchids be placed and grow on blocks other than End Stone.")
    public boolean hasEnderOrchidStrictPlacement = true;


    @Override
    public String getName() { return CoxinhaUtilities.MOD_ID; }

    @Override
    public @Nullable String getModid() { return CoxinhaUtilities.MOD_ID; }

}