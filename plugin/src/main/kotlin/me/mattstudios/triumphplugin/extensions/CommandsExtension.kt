package me.mattstudios.triumphplugin.extensions

import groovy.lang.Closure
import me.mattstudios.triumphplugin.extensions.components.Command
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author Matt
 */
open class CommandsExtension(private val project: Project) {

    private val commandContainer: NamedDomainObjectContainer<Command> = project.container(Command::class.java)

    open fun command(name: String, closure: Closure<*>) {
        project.configure(commandContainer.maybeCreate(name), closure)
    }

    fun build(): MutableMap<String, Map<String, Any?>> {
        val commands = mutableMapOf<String, Map<String, Any?>>()
        commandContainer.forEach {
            commands.putAll(it.build())
        }
        return commands
    }

}