package me.mattstudios.triumphplugin

import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import me.mattstudios.triumphplugin.constants.ANNOTATION_REPO
import me.mattstudios.triumphplugin.constants.BUKKIT_ANNOTATION
import me.mattstudios.triumphplugin.constants.EXTENSION_NAME
import me.mattstudios.triumphplugin.constants.PERSONAL_REPOSITORY
import me.mattstudios.triumphplugin.exceptions.MainClassException
import org.bukkit.configuration.file.YamlConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import java.io.File
import java.io.FileNotFoundException
import kotlin.reflect.full.memberProperties

class TriumphPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        with(project) {
            plugins.apply("JavaPlugin")

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

            buildPlugin.doLast { task ->
                var main: String? = null

                // Gets the classes folder
                val classesFolder = File(buildDir, "classes")
                val classesFiles = classesFolder.listFiles() ?: return@doLast

                // Loops here to check for folders like Java or Kotlin
                for (folder in classesFiles) {
                    if (folder.isDirectory.not()) continue

                    // Searches for the main folder
                    val folderMain = File(folder, "main")
                    // If main doesn't exist throws
                    if (folderMain.exists().not()) {
                        throw FileNotFoundException("Cold not find main folder!")
                    }

                    // Loops throw all the files in the folder, I love Kotlin
                    for (file in folderMain.walkBottomUp().filter { it.extension == "class" }) {

                        // ASM ClassReader and Node to check for the annotation
                        val classReader = ClassReader(file.readBytes())
                        val classNode = ClassNode()

                        classReader.accept(classNode, ClassReader.EXPAND_FRAMES)

                        // Gets the path to the class (package and class name)
                        val classPath = file.path.removeSuffix(".class")
                            .split("\\\\(kotlin|java)\\\\main\\\\".toRegex())
                            .last().replace("\\", ".")

                        // Checks for the main annotation
                        if (classNode.visibleAnnotations.map { it.desc }.find { BUKKIT_ANNOTATION in it } == null) {
                            continue
                        }

                        if (main != null) throw MainClassException("Multiple main classes were detected!")

                        main = classPath

                    }
                }

                if (main == null) throw MainClassException("Main class was not found!")

                println("Main class found: $main")

                println("creating file")
                val folder = File("${buildDir}/resources/main/")
                if (folder.exists().not()) folder.mkdirs()
                val test = File(folder, "plugin.yml")
                if (test.exists().not()) {
                    val configuration = YamlConfiguration()
                    configuration.set("main", main)
                    configuration.save(test)
                }
            }

            tasks.findByName("processResources")?.finalizedBy(buildPlugin)

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
