package dev.triumphteam.constants

import dev.triumphteam.annotations.BukkitMain

internal const val RESOURCES_TASK = "processResources"

internal const val PERSONAL_REPO = "https://repo.triumphteam.dev/{type}/"
internal val PERSONAL_RELEASES: String
    get() = PERSONAL_REPO.replace("{type}", "releases")

internal const val ANNOTATION_DEPENDENCY = "dev.triumphteam:triumph-gradle-annotations:0.0.1"

internal val BUKKIT_ANNOTATION = BukkitMain::class.java.simpleName

internal const val OLD_GROUP = "me.mattstudios"
internal const val GROUP = "dev.triumphteam"

internal const val LATEST_VERSION_DEFAULT = "+"