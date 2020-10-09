package me.mattstudios.triumphplugin.extensions.components

/**
 * @author Matt
 */

open class Permission(private val name: String) {
    var description: String? = null
    var defaultValue: String? = null

    fun build(): Map<String, Map<String, Any?>> {
        return mapOf(
            name to mapOf(
                "description" to description,
                "default" to defaultValue
            )
        )
    }
}