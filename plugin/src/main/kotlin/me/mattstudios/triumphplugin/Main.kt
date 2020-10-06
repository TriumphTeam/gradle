package me.mattstudios.triumphplugin

import java.io.File
import java.net.URLClassLoader

/**
 * @author Matt
 */
fun main() {
    val classesFolder = File("T:/Projects/Triumph/triumph-gradle-plugin/plugin-test/build/classes/")
    val classesFiles = classesFolder.listFiles() ?: return
    for (file in classesFiles) {
        if (file.isDirectory.not()) continue

        val main = File(file, "main")
        if (main.exists().not()) return

        val mainUrl = main.toURI().toURL()

        main.walkBottomUp().filter { it.extension == "class" }.forEach {
            val classLoader = URLClassLoader(arrayOf(mainUrl))
            val classPath = it.path.removeSuffix(".class").split("\\\\(\\w+)\\\\main\\\\".toRegex())
                .last().replace("\\", ".")
            val clazz = classLoader.loadClass(classPath)
            println(clazz)
            println(clazz.isAnnotationPresent(Deprecated::class.java))
        }
    }
}