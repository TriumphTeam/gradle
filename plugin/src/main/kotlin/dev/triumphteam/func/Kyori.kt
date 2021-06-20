package dev.triumphteam.func

import org.gradle.api.Project

fun Project.addAdventure(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addSonatype()
    dependencies.add(scope, "net.kyori:adventure-api:${data["version"]}")
}

fun Project.addPlatform(data: Map<String, Any>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addSonatype()
    getTypes(data, "bukkit").forEach {
        dependencies.add(scope, "net.kyori:adventure-platform-$it:${data["version"]}")
    }
}