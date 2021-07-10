package dev.triumphteam.helper

import org.gradle.kotlin.dsl.PluginDependenciesSpecScope
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpecScope.shadow(version: String): PluginDependencySpec {
    return id("com.github.johnrengelman.shadow") version(version)
}
