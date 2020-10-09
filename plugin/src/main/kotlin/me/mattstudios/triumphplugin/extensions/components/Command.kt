package me.mattstudios.triumphplugin.extensions.components

/**
 * @author Matt
 */
open class Command(private val name: String) {
    var description: String? = null
    var aliases = mutableListOf<String>()
    var permission: String? = null
    var permissionMessage: String? = null
    var usage: String? = null

    fun build(): Map<String, Map<String, Any?>> {
        return mapOf(
            name to mapOf(
                "description" to description,
                "aliases" to aliases,
                "permission" to permission,
                "permissionMessage" to permissionMessage,
                "usage" to usage
            )
        )
    }
}