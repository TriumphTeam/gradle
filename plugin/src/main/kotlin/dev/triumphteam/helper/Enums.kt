package dev.triumphteam.helper

enum class MinecraftVersion(val version: String) {

    // Old shit
    V1_8_8("1.8.8"),
    V1_12_2("1.12.2"),

    // New
    V1_16_5("1.16.5"),
    V1_17("1.17"),

}

enum class CorePlatform {
    BUKKIT,
    JDA,
}

enum class CoreFeature {
    CONFIG,
    LOCALE,
}
