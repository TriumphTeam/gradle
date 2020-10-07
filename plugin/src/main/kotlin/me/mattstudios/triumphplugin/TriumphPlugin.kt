package me.mattstudios.triumphplugin

import me.mattstudios.triumphplugin.constants.ANNOTATION_REPO
import me.mattstudios.triumphplugin.constants.EXTENSION_NAME
import me.mattstudios.triumphplugin.constants.PERSONAL_REPOSITORY
import me.mattstudios.triumphplugin.exceptions.MainClassException
import me.mattstudios.triumphplugin.extensions.BukkitExtension
import me.mattstudios.triumphplugin.func.findMainClass
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import java.io.File

class TriumphPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            plugins.apply("java")

            // Creates the main extension
            val extension = extensions.findByType(BukkitExtension::class.java) ?: extensions.create(
                EXTENSION_NAME,
                BukkitExtension::class.java,
                project
            )

            // Adds annotations to the project
            afterEvaluate {
                repositories.maven {
                    it.setUrl(PERSONAL_REPOSITORY)
                }

                dependencies.add(
                    JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
                    ANNOTATION_REPO
                )
            }

            val buildPlugin = project.task("buildYamlPlugin")

            buildPlugin.doLast {
                val main = buildDir.findMainClass() ?: throw MainClassException("Main class was not found!")

                println("creating file")

                val folder = File("${buildDir}/resources/main/")
                if (folder.exists().not()) folder.mkdirs()
                val pluginFile = File(folder, "plugin.yml")

                if (pluginFile.exists().not()) {
                    val configuration = extension.build(main)
                    configuration.save(pluginFile)
                }
            }

            tasks.findByName("processResources")?.finalizedBy(buildPlugin)

            afterEvaluate {

            }
        }

    }

}
