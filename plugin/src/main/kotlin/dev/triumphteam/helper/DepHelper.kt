package dev.triumphteam.helper

import org.gradle.api.artifacts.dsl.DependencyHandler

private const val OLD_GROUP = "me.mattstudios"
private const val GROUP = "dev.triumphteam"
private val OLD_PAPER_VERSIONS = (8..16)

fun DependencyHandler.paper(version: String): String {
    if (version.isOld()) return "com.destroystokyo.paper:paper-api:$version-R0.1-SNAPSHOT"
    return "io.papermc.paper:paper-api:$version-R0.1-SNAPSHOT"
}

fun DependencyHandler.spigot(version: String): String {
    return "org.spigotmc:spigot-api:$version-R0.1-SNAPSHOT"
}

fun DependencyHandler.nms(version: String): String {
    return "org.spigotmc:spigot:$version-R0.1-SNAPSHOT"
}

fun DependencyHandler.triumph(lib: String, version: String): String {
    return when (lib) {
        "cmd", "cmds" -> "$OLD_GROUP.utils:matt-framework:$version"
        "gui" -> "$GROUP:triumph-gui:$version"
        "markdown", "md" -> "$OLD_GROUP:triumph-msg-adventure:$version"
        "config" -> "$OLD_GROUP:triumph-config:$version"
        "core" -> "$GROUP:triumph-core:$version"
        else -> ""
    }
}

fun DependencyHandler.adventureApi(version: String): String {
    return "net.kyori:adventure-api:$version"
}

fun DependencyHandler.adventure(platform: String, version: String): String {
    return "net.kyori:adventure-platform-$platform:$version"
}

fun DependencyHandler.papi(version: String): String {
    return "me.clip:placeholderapi:$version"
}

private fun String.isOld(): Boolean {
    OLD_PAPER_VERSIONS.forEach {
        if (startsWith("1.$it")) return true
    }
    return false
}