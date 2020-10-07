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

    fun build() {
        println(name)
        println(description)
        println(aliases)
        println(permission)
        println(permissionMessage)
        println(usage)
    }
}