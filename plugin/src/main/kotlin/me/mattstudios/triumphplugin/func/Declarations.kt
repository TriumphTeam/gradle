package me.mattstudios.triumphplugin.func

import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import me.mattstudios.triumphplugin.constants.BUKKIT_ANNOTATION
import me.mattstudios.triumphplugin.constants.PERSONAL_REPOSITORY
import me.mattstudios.triumphplugin.exceptions.MainClassException
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.plugins.JavaPlugin
import java.io.File
import java.io.FileNotFoundException

private val scopes = mapOf(
    "implementation" to JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME,
    "compileonly" to JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
    "api" to JavaPlugin.API_CONFIGURATION_NAME
)

fun RepositoryHandler.addMain() {
    add(mavenCentral())
    add(mavenLocal())
    add(jcenter())
}

fun RepositoryHandler.addSonatype() {
    add("https://oss.sonatype.org/content/repositories/snapshots/")
}

fun RepositoryHandler.addMatt() {
    maven {
        it.setUrl(PERSONAL_REPOSITORY)
    }
}

fun RepositoryHandler.add(url: String) {
    maven {
        it.setUrl(url)
    }
}

fun getScope(data: Map<String, Any>, default: String): String? {
    if (data.isEmpty()) return null
    val scopeData = data["scope"] ?: default
    return if (scopeData !is String) scopes[default] else scopes[scopeData.toLowerCase()]
}

fun getTypes(data: Map<String, Any>, default: String): List<String> {
    val typeData = data["type"]

    val types = mutableListOf<String>()
    when (typeData) {
        is List<*> -> types.addAll(typeData.filterIsInstance<String>())
        is String -> types.add(typeData)
        else -> types.add(default)
    }

    return types
}


/**
 * Gets the main class fom the build directory
 */
fun File.findMainClass(): String? {
    var main: String? = null

    // Gets the classes folder
    val classesFolder = File(this, "classes")
    val classesFiles = classesFolder.listFiles() ?: throw FileNotFoundException("Cold not find classes folder!")

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

    return main
}