package me.mattstudios.triumphplugin

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author Matt
 */
open class BukkitExtension(project: Project) {

    var main: String? = null
    var name: String? = null
    var version: String? = null

    var description: String? = null
    var apiVersion: String? = null
    var load: Load? = null
    var author: String? = null
    var authors = mutableListOf<String>()
    var website: String? = null
    var prefix: String? = null

    var depend = mutableListOf<String>()
    var softdepend = mutableListOf<String>()

    var loadbefore = mutableListOf<String>()

    var commands: CommandsExtension = project.extensions.create("commands", CommandsExtension::class.java, project)
    var permissions: PermissionExtension = project.extensions.create("permissions", PermissionExtension::class.java, project)

}

open class CommandsExtension(private val project: Project) {

    private val commandContainer: NamedDomainObjectContainer<Command> = project.container(Command::class.java)

    open fun command(name: String, closure: Closure<*>) {
        project.configure(commandContainer.maybeCreate(name), closure)
    }

    fun test() {
        println(commandContainer)
        commandContainer.forEach { it.build() }
    }

}

open class Command(private val name: String) {
    var description: String? = null
    var aliases = mutableListOf<String>()
    var permission: String? = null
    var permissionMessage: String? = null
    var usage: String? = null

    fun build() {
        println(name)
        println(description)
        println(aliases)
        println(permission)
        println(permissionMessage)
        println(usage)
    }
}

open class PermissionExtension(private val project: Project) {

    private val permissionContainer: NamedDomainObjectContainer<Permission> = project.container(Permission::class.java)

    open fun permission(name: String, closure: Closure<*>) {
        project.configure(permissionContainer.maybeCreate(name), closure)
    }

    fun test() {
        println(permissionContainer)
        permissionContainer.forEach { it.build() }
    }

}

open class Permission(private val name: String) {
    var description: String? = null
    var default: String? = null

    fun build() {
        println(name)
        println(description)
        println(default)
    }
}