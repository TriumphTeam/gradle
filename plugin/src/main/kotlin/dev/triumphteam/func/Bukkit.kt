package dev.triumphteam.func

import org.gradle.api.Project

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