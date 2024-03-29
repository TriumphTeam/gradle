package dev.triumphteam.func

import dev.triumphteam.constants.BUKKIT_ANNOTATION
import dev.triumphteam.exceptions.MainClassException
import dev.triumphteam.nebula.ModularPlugin
import org.gradle.api.logging.Logger
import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

private val OLD_PAPER_VERSIONS = (8..16)

internal fun Logger.info(block: () -> String) {
    info(block())
}

/**
 * Extension function to remove a string from a string.
 */
internal fun String.remove(string: String): String {
    return replace(string, "")
}

/**
 * Gets the main class fom the build directory
 */
internal fun File.findMainClass(): String? {
    var main: String? = null

    // Gets the classes folder
    val classesFolder = File(this, "classes")
    if (!classesFolder.exists()) classesFolder.mkdirs()
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
            val classPath = file.path
                .remove(folderMain.path)
                .removePrefix(File.separator)
                .replace(File.separator, ".")
                .removeSuffix(".class")

            val isAnnotated =
                classNode.invisibleAnnotations?.map { it.desc }?.any { BUKKIT_ANNOTATION in it } == true

            val isModularPlugin = ModularPlugin::class.java.simpleName in classNode.superName

            // If not a triumph plugin or not annotated, return
            if (!isAnnotated && !isModularPlugin) {
                continue
            }
            
            if (main != null) throw MainClassException("Multiple main classes were detected!")

            main = classPath
        }
    }

    return main
}

internal fun Path.createFileIfNotExists() {
    if (Files.exists(this)) return

    if (!Files.exists(parent) || !Files.isDirectory(parent)) {
        try {
            Files.createDirectories(parent)
        } catch (e: IOException) {
            throw Exception("Failed to create parent folders for '$this'", e)
        }
    }
    try {
        Files.createFile(this)
    } catch (e: IOException) {
        throw Exception("Failed to create file '$this'", e)
    }
}

internal fun String.isOld(): Boolean {
    OLD_PAPER_VERSIONS.forEach {
        if (startsWith("1.$it")) return true
    }
    return false
}
