@file:OptIn(ExperimentalStdlibApi::class)

package dev.triumphteam.extensions.components

class CommandsBuilder {

    private val commands = mutableMapOf<String, Map<String, Any>>()

    fun command(name: String, command: Command.() -> Unit = {}) {
        commands[name] = Command().apply(command).toMap()
    }

    internal fun build(): Map<String, Map<String, Any>> {
        return commands
    }

}

data class Command(
    var description: String? = null,
    var aliases: List<String>? = null,
    var permission: String? = null,
    var permissionMessage: String? = null,
    var usage: String? = null
) {
    internal fun toMap(): Map<String, Any> {
        return buildMap {
            description?.let { put("description", it) }
            aliases?.let { put("alias", it) }
            permission?.let { put("permission", it) }
            permissionMessage?.let { put("permission-message", it) }
            usage?.let { put("usage", it) }
        }
    }
}