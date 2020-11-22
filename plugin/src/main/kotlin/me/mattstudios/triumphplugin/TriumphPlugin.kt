package me.mattstudios.triumphplugin

import me.mattstudios.triumphplugin.constants.ANNOTATION_DEPENDENCY
import me.mattstudios.triumphplugin.constants.RESOURCES_TASK
import me.mattstudios.triumphplugin.exceptions.MainClassException
import me.mattstudios.triumphplugin.exceptions.RequiredValueNotFoundException
import me.mattstudios.triumphplugin.extensions.BukkitExtension
import me.mattstudios.triumphplugin.extensions.TriumphExtension
import me.mattstudios.triumphplugin.func.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.plugins.JavaPlugin
import java.io.File


class TriumphPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            plugins.apply("java")

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
                    dependencies.add(
                        JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
                        ANNOTATION_DEPENDENCY
                    )
                }

                with(triumphExtension) {
                    addSpigot(spigotData)
                    addPaper(paperData)
                    addNms(nmsData)
                    addCommands(cmdsData)
                    addMessages(msgsData)
                    addGui(guiData)
                    addConfig(configData)
                    addCore(coreData)
                }
            }

            val buildPlugin = project.task("buildYamlPlugin")

            buildPlugin.doLast {
                bukkitExtension.name ?: return@doLast

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
