package me.mattstudios.triumphplugin

import org.bukkit.configuration.file.YamlConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import kotlin.reflect.full.memberProperties


private const val EXTENSION_NAME = "bukkit"

class TriumphPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            val extension = extensions.findByType(BukkitExtension::class.java) ?: extensions.create(
                EXTENSION_NAME,
                BukkitExtension::class.java,
                project
            )

            val shit = project.task("shit")

            shit.doLast {
                println("creating file")
                val folder = File("${buildDir}/resources/main/")
                if (folder.exists().not()) folder.mkdirs()
                val test = File(folder, "shio.yml")
                if (test.exists().not()) {
                    val configuration = YamlConfiguration()
                    configuration.set("hello", "fuck")
                    configuration.save(test)
                }
            }

            tasks.findByName("processResources")?.finalizedBy(shit)

            afterEvaluate {
                for (prop in BukkitExtension::class.memberProperties) {
                    println("${prop.name} = ${prop.get(extension)}")
                }

                println()
                extension.commands.test()
            }
        }

    }

}
