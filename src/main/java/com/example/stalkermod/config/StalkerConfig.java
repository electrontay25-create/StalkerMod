package com.example.stalkermod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class StalkerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue STALK_COOLDOWN;
    public static final ForgeConfigSpec.IntValue DOOR_COOLDOWN;
    public static final ForgeConfigSpec.IntValue BLINDNESS_DURATION;

    static {
        BUILDER.push("Configuración del Stalker");
        STALK_COOLDOWN = BUILDER.comment("Cooldown en segundos para el evento de acecho").defineInRange("stalkCooldown", 300, 30, 3600);
        DOOR_COOLDOWN = BUILDER.comment("Cooldown en segundos para el evento de la puerta").defineInRange("doorCooldown", 400, 30, 3600);
        BLINDNESS_DURATION = BUILDER.comment("Duración de la ceguera en segundos al romper la puerta").defineInRange("blindnessDuration", 10, 1, 60);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
