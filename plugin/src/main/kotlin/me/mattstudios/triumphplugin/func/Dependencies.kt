package me.mattstudios.triumphplugin.func

import me.mattstudios.triumphplugin.constants.PERSONAL_REPOSITORY
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.plugins.JavaPlugin

/**
 * @author Matt
 */

private val scopes = mapOf(
    "implementation" to JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
    "compileonly" to JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
    "api" to JavaPlugin.API_CONFIGURATION_NAME
)

fun RepositoryHandler.addMain() {
    add(mavenCentral())
    add(mavenLocal())
    maven {
        it.setUrl(PERSONAL_REPOSITORY)
    }
}

fun RepositoryHandler.add(url: String) {
    maven {
        it.setUrl(url)
    }
}

fun getScope(data: Map<String, String>, default: String): String? {
    if (data.isEmpty()) return null
    val scopeData = data["scope"] ?: default
    return scopes[scopeData.toLowerCase()]
}

fun Project.addCommands(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    dependencies.add(scope, "me.mattstudios.utils:matt-framework:${data["version"]}")
}

fun Project.addMessages(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    val typeData = data["type"] ?: "bukkit"
    val type = if ("adventure" == typeData || "bukkit" == typeData) typeData else "bukkit"
    dependencies.add(scope, "me.mattstudios:mf-msg-$type:${data["version"]}")
}

fun Project.addConfig(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    dependencies.add(scope, "me.mattstudios:triumph-config:${data["version"]}")
}

fun Project.addCore(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    dependencies.add(scope, "me.mattstudios:triumph-core:${data["version"]}")
}

fun Project.addGui(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    dependencies.add(scope, "me.mattstudios.utils:matt-framework-gui:${data["version"]}")
}

fun Project.addSpigot(data: Map<String, String>) {
    val scope = getScope(data, "compileOnly") ?: return
    repositories.add("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    dependencies.add(scope, "org.spigotmc:spigot-api:${data["version"]}-R0.1-SNAPSHOT")
}

fun Project.addNms(data: Map<String, String>) {
    val scope = getScope(data, "compileOnly") ?: return
    dependencies.add(scope, "org.spigotmc:spigot:${data["version"]}-R0.1-SNAPSHOT")
}

fun Project.addPaper(data: Map<String, String>) {
    val scope = getScope(data, "compileOnly") ?: return
    repositories.add("https://papermc.io/repo/repository/maven-public/")
    dependencies.add(scope, "com.destroystokyo.paper:paper-api:${data["version"]}-R0.1-SNAPSHOT")
}
