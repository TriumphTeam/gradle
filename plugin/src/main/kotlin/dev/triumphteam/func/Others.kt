package dev.triumphteam.func

import org.gradle.api.Project

fun Project.addPapi(data: Map<String, String>) {
    val scope = getScope(data, "compileOnly") ?: return
    repositories.add("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    dependencies.add(scope, "me.clip:placeholderapi:${data["version"]}")
}

fun Project.addCloud(data: Map<String, Any>) {
    val scope = getScope(data, "compileOnly") ?: return
    repositories.addSonatype()
    getTypes(data, "bukkit").forEach {
        dependencies.add(scope, "cloud.commandframework:cloud-$it:${data["version"]}")
    }
}