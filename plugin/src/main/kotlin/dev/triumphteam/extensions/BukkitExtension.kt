@file:OptIn(ExperimentalStdlibApi::class)

package dev.triumphteam.extensions

import dev.triumphteam.extensions.components.CommandsBuilder
import dev.triumphteam.extensions.components.DependencyBuilder
import dev.triumphteam.extensions.components.PermissionsBuilder
import org.gradle.api.Project

/**
 * All the setter override is to create a simple check if the extension is being used or not, since that's not provided by gradle.
 */
@Suppress("MemberVisibilityCanBePrivate")
open class BukkitExtension(private val project: Project) {

    internal var used = false
        private set

    var name: String? = null
        set(value) {
            used = true
            field = value
        }

    var version: String? = null
        set(value) {
            used = true
            field = value
        }

    var description: String? = null
        set(value) {
            used = true
            field = value
        }

    var apiVersion: String? = null
        set(value) {
            used = true
            field = value
        }

    var load: String? = null
        set(value) {
            used = true
            field = value
        }

    var author: String? = null
        set(value) {
            used = true
            field = value
        }

    var authors: List<String>? = null
        set(value) {
            used = true
            field = value
        }

    var website: String? = null
        set(value) {
            used = true
            field = value
        }

    var prefix: String? = null
        set(value) {
            used = true
            field = value
        }

    private val depend = mutableListOf<String>()

    private val softDepend = mutableListOf<String>()

    var loadbefore: List<String>? = null
        set(value) {
            used = true
            field = value
        }

    private var commands: CommandsBuilder? = null
        set(value) {
            used = true
            field = value
        }

    private var permissions: PermissionsBuilder? = null
        set(value) {
            used = true
            field = value
        }

    fun depends(depends: DependencyBuilder.() -> Unit) {
        depend.addAll(DependencyBuilder().apply(depends).build())
    }

    fun softDepends(depends: DependencyBuilder.() -> Unit) {
        softDepend.addAll(DependencyBuilder().apply(depends).build())
    }

    fun commands(commands: CommandsBuilder.() -> Unit) {
        this.commands = CommandsBuilder().apply(commands)
    }

    fun permissions(commands: PermissionsBuilder.() -> Unit) {
        this.permissions = PermissionsBuilder().apply(commands)
    }

    internal fun build(main: String): Map<String, Any> {
        return buildMap {
            add("main", main)
            add("name", name ?: project.name)
            add("version", version ?: project.version.toString())
            add("api-version", apiVersion)
            add("description", description)
            add("load", load)
            add("author", author)
            add("authors", authors)
            add("website", website)
            if (depend.isNotEmpty()) add("depend", depend)
            add("prefix", prefix)
            if (depend.isNotEmpty()) add("softdepend", softDepend)
            add("loadbefore", loadbefore)
            add("commands", commands?.build())
            add("permissions", permissions?.build())
        }
    }

    private fun MutableMap<String, Any>.add(key: String, value: Any?) {
        value?.let {
            this[key] = it
        }
    }

}