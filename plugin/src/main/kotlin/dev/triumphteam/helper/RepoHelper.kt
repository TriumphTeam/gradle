package dev.triumphteam.helper

import dev.triumphteam.constants.PERSONAL_REPO
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectChecker.type
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.spigot(): MavenArtifactRepository {
    return maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

fun RepositoryHandler.paper(): MavenArtifactRepository {
    return maven("https://papermc.io/repo/repository/maven-public/")
}

fun RepositoryHandler.papi(): MavenArtifactRepository {
    return maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

fun RepositoryHandler.sonatype(): MavenArtifactRepository {
    return maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

fun RepositoryHandler.triumph(
    type: String = "releases",
    block: MavenArtifactRepository.() -> Unit,
): MavenArtifactRepository {
    return maven(PERSONAL_REPO.replace("{type}", type), block)
}