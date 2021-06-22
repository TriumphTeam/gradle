@file:OptIn(ExperimentalStdlibApi::class)

package dev.triumphteam.extensions.components

class PermissionsBuilder {

    private val permissions = mutableMapOf<String, Map<String, Any>>()

    fun permission(name: String, permission: Permission.() -> Unit = {}) {
        permissions[name] = Permission().apply(permission).build()
    }

    internal fun build(): Map<String, Map<String, Any>> {
        return permissions
    }

}

data class Permission(
    var description: String? = null,
    var default: String? = null,
) {
    private val children = mutableMapOf<String, Boolean>()

    fun children(vararg children: Pair<String, Boolean>) {
        this.children.putAll(children)
    }

    internal fun build(): Map<String, Any> {
        return buildMap {
            description?.let { put("description", it) }
            default?.let { put("default", it) }
            if (children.isNotEmpty()) {
                put("children", children)
            }
        }
    }

}