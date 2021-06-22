package dev.triumphteam.extensions

import org.gradle.api.Project

/**
 * @author Matt
 */
open class BukkitExtension(private val project: Project) {

    var name: String? = null
    var version: String? = null

    var description: String? = null
    var apiVersion: String? = null
    var load: String? = null
    var author: String? = null
    var authors = mutableListOf<String>()
    var website: String? = null
    var prefix: String? = null

    var depend = mutableListOf<String>()
    var softdepend = mutableListOf<String>()

    var loadbefore = mutableListOf<String>()

    var commands: CommandsExtension = project.extensions.create("commands", CommandsExtension::class.java, project)
    var permissions: PermissionsExtension =
        project.extensions.create("permissions", PermissionsExtension::class.java, project)

    /*fun build(main: String): YamlConfiguration {
        val configuration = YamlConfiguration()

        configuration.apply {
            set("main", main)
            setRequired("name", this@BukkitExtension.name)
            set("version", version ?: project.version)
            set("description", description)
            set("load", load)
            set("author", author)
            setList("authors", authors)
            set("website", website)
            set("prefix", prefix)
            setList("depend", depend)
            setList("softdepend", softdepend)
            setList("loadbefore", loadbefore)
            set("api-version", apiVersion)
            set("commands", commands.build())
            set("permissions", permissions.build())
        }

        return configuration
    }

    private fun YamlConfiguration.setRequired(key: String, value: String?) {
        if (value == null) throw RequiredValueNotFoundException("Required value ($key) has not been defined!")
        set(key, value)
    }

    private fun YamlConfiguration.setList(key: String, list: List<String>) {
        if (list.isEmpty()) return
        set(key, list)
    }
*/
}