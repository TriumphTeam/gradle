package dev.triumphteam.helper

enum class MinecraftVersion(val version: String) {

    // Old shit
    V1_8_8("1.8.8"),
    V1_12_2("1.12.2"),

    // New
    V1_16_5("1.16.5"),
    V1_17_1("1.17.1"),
    V1_18("1.18"),

}

enum class PlatformType(val repo: String) {
    BUKKIT("bukkit"),
    JDA("jda"),
    NONE(""),
}

enum class Library(val repo: String) {
    CMDS("cmds"),
    GUI("gui"),
}

enum class Feature(val repo: String) {
    CONFIG("config"),
    LOCALE("locale"),
    SCHEDULER("scheduler"),
    COMMANDS("commands"),
    LISTENERS("listeners"),
}