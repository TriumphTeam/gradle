package me.mattstudios.triumphplugin

import org.bukkit.configuration.file.YamlConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.io.File
import java.net.URLClassLoader
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import kotlin.reflect.full.memberProperties


private const val EXTENSION_NAME = "bukkit"
private const val RESOURCES_TASK = "processResources"
private const val SPIGOT_TASK = "buildSpigot"

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
                val classes = File("${buildDir}/classes/kotlin/main/")
                val url = classes.toURI().toURL()
                val classLoader = URLClassLoader(arrayOf(url))



                /*val packge = url.toString().split("/main/").last().replace("/", ".")
                val clazz = classLoader.loadClass("me.mattstudios.testing.MainClass.class")
                println(clazz)
                println(clazz.isAnnotationPresent(EventHandler::class.java))*/
                /*classes.walkBottomUp().filter { it.extension == "class" }.forEach {
                    val url = it.toURI().toURL()
                    val classLoader = URLClassLoader(arrayOf(url))
                    val packge = url.toString().split("/main/").last().replace("/", ".")
                    val clazz = classLoader.loadClass(packge)
                    println(clazz)
                    println(clazz.isAnnotationPresent(EventHandler::class.java))
                }*/

                println("creating file")
                val folder = File("${buildDir}/resources/main/")
                if (folder.exists().not()) folder.mkdirs()
                val test = File(folder, "plugin.yml")
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
