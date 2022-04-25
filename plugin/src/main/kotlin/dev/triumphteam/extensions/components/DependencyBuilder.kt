package dev.triumphteam.extensions.components

import org.gradle.api.Project

class DependencyBuilder {

    private val depends = mutableListOf<String>()

    fun on(name: String) {
        depends.add(name)
    }

    fun on(project: Project) {
        depends.add(project.name)
    }

    internal fun build(): MutableList<String> {
        return depends
    }
}