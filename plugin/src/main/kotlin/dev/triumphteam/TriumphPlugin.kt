package dev.triumphteam

import dev.triumphteam.constants.ANNOTATION_DEPENDENCY
import dev.triumphteam.constants.PERSONAL_REPOSITORY
import dev.triumphteam.constants.RESOURCES_TASK
import dev.triumphteam.exceptions.MainClassException
import dev.triumphteam.extensions.BukkitExtension
import dev.triumphteam.func.createFileIfNotExists
import dev.triumphteam.func.findMainClass
import dev.triumphteam.func.info
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.maven
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.nio.file.Paths

class TriumphPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        plugins.apply("java")

        // Creates the main extension
        val bukkitExtension = extensions.findByType(BukkitExtension::class.java) ?: extensions.create(
            "bukkit",
            BukkitExtension::class.java,
            this
        )

        // Adds annotations to the project
        afterEvaluate {
            allprojects {
                repositories.maven(PERSONAL_REPOSITORY)
                dependencies.add(
                    JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
                    ANNOTATION_DEPENDENCY
                )
            }
        }

        val buildPlugin = project.task("buildYamlPlugin")

        buildPlugin.doLast {
            if (!bukkitExtension.used) return@doLast

            val main = buildDir.findMainClass() ?: throw MainClassException("Main class was not found!")

            logger.info { "Creating `plugin.yml` file!" }

            val pluginPath = Paths.get(buildDir.path, "resources", "main", "plugin.yml")
            pluginPath.createFileIfNotExists()

            val dumpOptions = DumperOptions().apply {
                defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
                isPrettyFlow = true
                isAllowUnicode = true
            }

            val yaml = Yaml(dumpOptions)
            yaml.dump(bukkitExtension.build(main), pluginPath.toFile().writer())

        }

        tasks.findByName(RESOURCES_TASK)?.finalizedBy(buildPlugin)
    }
}