package com.github.Pandarix.beautify.util;


import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final String CATEGORY_BLINDS = "blinds";
    public static final String CATEGORY_GENERATION = "generation";

    public static final ModConfigSpec.IntValue SEARCHRADIUS;
    public static final ModConfigSpec.BooleanValue OPENS_FROM_ROOT;
    public static final ModConfigSpec.IntValue BOTANIST_SPAWN_WEIGHT;

    public static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();

        SERVER_BUILDER
                .comment("Settings for the Blinds")
                .push("CATEGORY_BLINDS");

        SEARCHRADIUS = SERVER_BUILDER
                .comment("Searches X-Blocks below and X/2 to the sides of the clicked blind for others and opens/closes them too")
                .defineInRange("searchRadius", 6, 0, 100);

        OPENS_FROM_ROOT = SERVER_BUILDER
                .comment("Opens blinds from the topmost blind on if true")
                .define("opensFromRoot", true);

        SERVER_BUILDER.pop();

        SERVER_BUILDER
                .comment("Settings for the Botanist Villager")
                .push("CATEGORY_GENERATION");

        BOTANIST_SPAWN_WEIGHT = SERVER_BUILDER
                .comment("Defines the chance of a Botanist Villager House inside a village")
                .defineInRange("botanistSpawnWeight", 2, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();

        SPEC = SERVER_BUILDER.build();
    }
}
