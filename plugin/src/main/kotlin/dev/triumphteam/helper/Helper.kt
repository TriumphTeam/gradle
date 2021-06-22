package dev.triumphteam.helper

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.spigotRepo(): MavenArtifactRepository {
    return maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

fun RepositoryHandler.paperRepo(): MavenArtifactRepository {
    return maven("https://papermc.io/repo/repository/maven-public/")
}

fun DependencyHandler.paperApi(version: String): String {
    return "io.papermc.paper:paper-api:$version-R0.1-SNAPSHOT"
}