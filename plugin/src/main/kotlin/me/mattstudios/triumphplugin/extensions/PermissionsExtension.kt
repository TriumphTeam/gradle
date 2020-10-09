package me.mattstudios.triumphplugin.extensions

import groovy.lang.Closure
import me.mattstudios.triumphplugin.extensions.components.Permission
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author Matt
 */
open class PermissionsExtension(private val project: Project) {

    private val permissionContainer: NamedDomainObjectContainer<Permission> = project.container(Permission::class.java)

    open fun permission(name: String, closure: Closure<*>) {
        project.configure(permissionContainer.maybeCreate(name), closure)
    }

    fun build(): MutableMap<String, Map<String, Any?>> {
        val permissions = mutableMapOf<String, Map<String, Any?>>()
        permissionContainer.forEach {
            permissions.putAll(it.build())
        }
        return permissions
    }

}