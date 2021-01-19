package me.mattstudios.triumphplugin.func

import org.gradle.api.Project

fun Project.addCommands(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addMatt()
    dependencies.add(scope, "me.mattstudios.utils:matt-framework:${data["version"]}")
}

fun Project.addMessages(data: Map<String, Any>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addMatt()
    getTypes(data, "bukkit").forEach {
        dependencies.add(scope, "me.mattstudios:triumph-msg-$it:${data["version"]}")
    }
}

fun Project.addConfig(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addMatt()
    dependencies.add(scope, "me.mattstudios:triumph-config:${data["version"]}")
}

fun Project.addCore(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addMatt()
    dependencies.add(scope, "me.mattstudios:triumph-core:${data["version"]}")
}

fun Project.addGui(data: Map<String, String>) {
    val scope = getScope(data, "implementation") ?: return
    repositories.addMatt()
    dependencies.add(scope, "me.mattstudios.utils:matt-framework-gui:${data["version"]}")
}