package me.mattstudios.triumphplugin

import com.google.gson.GsonBuilder
import dev.triumphteam.constants.ANNOTATION_DEPENDENCY
import dev.triumphteam.constants.RESOURCES_TASK
import dev.triumphteam.exceptions.MainClassException
import dev.triumphteam.extensions.BukkitExtension
import dev.triumphteam.extensions.TriumphExtension
import dev.triumphteam.func.addAdventure
import dev.triumphteam.func.addCloud
import dev.triumphteam.func.addCommands
import dev.triumphteam.func.addConfig
import dev.triumphteam.func.addCore
import dev.triumphteam.func.addGui
import dev.triumphteam.func.addMain
import dev.triumphteam.func.addMatt
import dev.triumphteam.func.addMessages
import dev.triumphteam.func.addNms
import dev.triumphteam.func.addPaper
import dev.triumphteam.func.addPapi
import dev.triumphteam.func.addPlatform
import dev.triumphteam.func.addSpigot
import dev.triumphteam.func.findMainClass
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.logging.LogLevel
import org.gradle.api.plugins.JavaPlugin
import java.io.File


class TriumphPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            plugins.apply("java")
            val assConfig = createConfig("ass", JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME)

            // Creates the main extension
            val bukkitExtension = extensions.findByType(BukkitExtension::class.java) ?: extensions.create(
                "bukkit",
                BukkitExtension::class.java,
                this
            )

            // Creates the main extension
            val triumphExtension = extensions.findByType(TriumphExtension::class.java) ?: extensions.create(
                "triumph",
                TriumphExtension::class.java
            )

            // Adds annotations to the project
            afterEvaluate {
                repositories.addMain()

                if (bukkitExtension.name != null) {
                    repositories.addMatt()
                    dependencies.add(
                        JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
                        ANNOTATION_DEPENDENCY
                    )
                }

                with(triumphExtension) {
                    // Bukkit
                    addSpigot(spigotData)
                    addPaper(paperData)
                    addNms(nmsData)

                    // Triumph
                    addCommands(cmdsData)
                    addMessages(msgsData)
                    addGui(guiData)
                    addConfig(configData)
                    addCore(coreData)

                    // Kyori
                    addPlatform(platformsData)
                    addAdventure(adventureData)

                    // Other
                    addPapi(papiData)
                    addCloud(cloudData)

                }
            }

            val buildPlugin = project.task("buildYamlPlugin")

            buildPlugin.doLast {
                bukkitExtension.name ?: return@doLast

                //val config = project.configurations.getByName("runtimeClassPath") ?: return@doLast
                val dependencies = assConfig.incoming.resolutionResult.allDependencies
                    .map { it.requested }
                    .toSet()
                    .map {
                        val (groupId, artifactId, version) = it.displayName.split(":")
                        Dependency("todo", groupId, artifactId, version)
                    }.toSet()

                val relocations = setOf(
                    Relocation("test.package.from", "test.package.to"),
                    Relocation("test.package.from2", "test.package.to2"),
                    Relocation("test.package.from3", "test.package.to3"),
                )
                println(
                    GsonBuilder().setPrettyPrinting().create()
                        .toJson(DependencyData(dependencies, relocations))
                )
                println("  ---  ")
                println(
                    GsonBuilder().setPrettyPrinting().create()
                        .toJson(
                            repositories.filterIsInstance<MavenArtifactRepository>()
                                .map { it.url.toString() }
                                .filter { !it.startsWith("file") }
                                .toSet()
                        )
                )

                val main = buildDir.findMainClass() ?: throw MainClassException("Main class was not found!")

                logger.log(LogLevel.INFO, "Creating `plugin.yml` file!")

                val folder = File("${buildDir}/resources/main/")
                if (folder.exists().not()) folder.mkdirs()
                val pluginFile = File(folder, "plugin.yml")

                if (pluginFile.exists().not()) {
                    val configuration = bukkitExtension.build(main)
                    configuration.save(pluginFile)
                }
            }

            tasks.findByName(RESOURCES_TASK)?.finalizedBy(buildPlugin)

        }

    }

}

internal fun Project.createConfig(configName: String, extends: String): Configuration {
    val compileOnlyConfig = configurations.findByName(extends) ?: throw RuntimeException("ass")

    val slimConfig = configurations.create(configName)
    compileOnlyConfig.extendsFrom(slimConfig)
    // TODO need to test this one, probably doesn't do what I think it does
    slimConfig.isTransitive = true

    return slimConfig
}

data class DependencyData(
    val dependencies: Set<Dependency>,
    val relocations: Set<Relocation>,
)

data class Dependency(
    val url: String,
    val groupId: String,
    val artifactId: String,
    val version: String,
)

data class Relocation(
    val original: String,
    val relocated: String,
)