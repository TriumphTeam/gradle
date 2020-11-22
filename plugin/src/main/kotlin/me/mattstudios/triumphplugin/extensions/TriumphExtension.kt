package me.mattstudios.triumphplugin.extensions

import me.mattstudios.triumphplugin.exceptions.RequiredValueNotFoundException
import org.bukkit.configuration.file.YamlConfiguration
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.CacheableTask

/**
 * @author Matt
 */
open class TriumphExtension {

    internal val spigotData = mutableMapOf<String, String>()
    internal val paperData= mutableMapOf<String, String>()
    internal val nmsData= mutableMapOf<String, String>()
    internal val cmdsData= mutableMapOf<String, String>()
    internal val msgsData= mutableMapOf<String, String>()
    internal val guiData= mutableMapOf<String, String>()
    internal val configData= mutableMapOf<String, String>()
    internal val coreData= mutableMapOf<String, String>()

    fun spigot(spigotData: Map<String, String>): TriumphExtension {
        if (spigotData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `spigot` doesn't have version")
        }

        this.spigotData.putAll(spigotData)
        return this
    }

    fun spigot(version: String): TriumphExtension {
        this.spigotData["version"] = version
        return this
    }

    fun paper(paperData: Map<String, String>): TriumphExtension {
        if (spigotData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `paper` doesn't have version")
        }

        this.paperData.putAll(paperData)
        return this
    }

    fun paper(version: String): TriumphExtension {
        this.paperData["version"] = version
        return this
    }

    fun nms(nmsData: Map<String, String>): TriumphExtension {
        if (nmsData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `nms` doesn't have version")
        }

        this.nmsData.putAll(nmsData)
        return this
    }

    fun nms(version: String): TriumphExtension {
        this.nmsData["version"] = version
        return this
    }

    fun cmds(cmdsData: Map<String, String>): TriumphExtension {
        if (cmdsData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `cmds` doesn't have version")
        }

        this.cmdsData.putAll(cmdsData)
        return this
    }

    fun cmds(version: String): TriumphExtension {
        this.cmdsData["version"] = version
        return this
    }

    fun msgs(msgsData: Map<String, String>): TriumphExtension {
        if (msgsData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `msgs` doesn't have version")
        }

        this.msgsData.putAll(msgsData)
        return this
    }

    fun msgs(version: String): TriumphExtension {
        this.msgsData["version"] = version
        return this
    }

    fun gui(guiData: Map<String, String>): TriumphExtension {
        if (guiData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `gui` doesn't have version")
        }

        this.guiData.putAll(guiData)
        return this
    }

    fun gui(version: String): TriumphExtension {
        this.guiData["version"] = version
        return this
    }

    fun config(configData: Map<String, String>): TriumphExtension {
        if (configData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `config` doesn't have version")
        }

        this.configData.putAll(configData)
        return this
    }

    fun config(version: String): TriumphExtension {
        this.configData["version"] = version
        return this
    }

    fun core(coreData: Map<String, String>): TriumphExtension {
        if (coreData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `core` doesn't have version")
        }

        this.coreData.putAll(coreData)
        return this
    }

    fun core(version: String): TriumphExtension {
        this.coreData["version"] = version
        return this
    }

}