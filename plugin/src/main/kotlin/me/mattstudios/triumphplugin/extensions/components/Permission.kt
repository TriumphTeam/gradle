package me.mattstudios.triumphplugin.extensions.components

/**
 * @author Matt
 */

open class Permission(private val name: String) {
    var description: String? = null
    var default: String? = null

    fun build() {
        println(name)
        println(description)
        println(default)
    }
}