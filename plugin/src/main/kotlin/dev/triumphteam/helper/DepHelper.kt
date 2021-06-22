package dev.triumphteam.helper

import dev.triumphteam.constants.GROUP
import dev.triumphteam.constants.OLD_GROUP
import dev.triumphteam.func.isOld
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPlugin

fun DependencyHandler.compileOnly(vararg dependencies: String) {
    dependencies.forEach { add(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME, it) }
}

fun DependencyHandler.implementation(vararg dependencies: String) {
    dependencies.forEach { add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, it) }
}

fun DependencyHandler.paper(version: MinecraftVersion) = paper(version.version)

fun DependencyHandler.paper(version: String): String {
    if (version.isOld()) return "com.destroystokyo.paper:paper-api:$version-R0.1-SNAPSHOT"
    return "io.papermc.paper:paper-api:$version-R0.1-SNAPSHOT"
}

fun DependencyHandler.spigot(version: MinecraftVersion) = spigot(version.version)

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