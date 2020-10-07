package me.mattstudios.triumphplugin

import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.tree.ClassNode
import java.io.File


/**
 * @author Matt
 */
fun main() {

    var mainClass: String? = null

    val classesFolder = File("T:/Projects/Triumph/triumph-gradle-plugin/plugin-test/build/classes/")
    val classesFiles = classesFolder.listFiles() ?: return
    for (file in classesFiles) {
        if (file.isDirectory.not()) continue

        val main = File(file, "main")
        if (main.exists().not()) return

        main.walkBottomUp().filter { it.extension == "class" }.forEach {

            val bytes = it.readBytes()

            val classReader = ClassReader(bytes)
            val classNode = ClassNode()

            classReader.accept(classNode, ClassReader.EXPAND_FRAMES)
            println(it.name)
            val classPath = it.path.removeSuffix(".class")
                .split("\\\\(kotlin|java)\\\\main\\\\".toRegex())
                .last().replace("\\", ".")

            if ("Lme/mattstudios/annotations/BukkitPlugin;" in classNode.visibleAnnotations.map { ann -> ann.desc }) {
                mainClass = classPath
            }

        }

        if (mainClass == null) {
            throw RuntimeException("No main class found")
        } else {
            println("Main class found: $mainClass")
        }
    }
}