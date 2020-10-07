package me.mattstudios.triumphplugin.func

import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import me.mattstudios.triumphplugin.constants.BUKKIT_ANNOTATION
import me.mattstudios.triumphplugin.exceptions.MainClassException
import java.io.File
import java.io.FileNotFoundException

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