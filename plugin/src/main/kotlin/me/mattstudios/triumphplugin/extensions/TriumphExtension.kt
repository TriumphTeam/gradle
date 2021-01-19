package me.mattstudios.triumphplugin.extensions

import me.mattstudios.triumphplugin.exceptions.RequiredValueNotFoundException

/**
 * @author Matt
 */
open class TriumphExtension {

    internal val spigotData = mutableMapOf<String, String>()
    internal val paperData = mutableMapOf<String, String>()
    internal val nmsData = mutableMapOf<String, String>()
    internal val cmdsData = mutableMapOf<String, String>()
    internal val msgsData = mutableMapOf<String, Any>()
    internal val guiData = mutableMapOf<String, String>()
    internal val configData = mutableMapOf<String, String>()
    internal val coreData = mutableMapOf<String, String>()
    internal val adventureData = mutableMapOf<String, String>()
    internal val platformsData = mutableMapOf<String, Any>()
    internal val cloudData = mutableMapOf<String, Any>()
    internal val papiData = mutableMapOf<String, String>()

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

    fun msgs(msgsData: Map<String, Any>): TriumphExtension {
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

    fun adventure(adventureData: Map<String, String>): TriumphExtension {
        if (adventureData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `core` doesn't have version")
        }

        this.adventureData.putAll(adventureData)
        return this
    }

    fun adventure(version: String): TriumphExtension {
        this.adventureData["version"] = version
        return this
    }

    fun platform(platformData: Map<String, Any>): TriumphExtension {
        if (platformData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `core` doesn't have version")
        }

        this.platformsData.putAll(platformData)
        return this
    }

    fun platform(version: String): TriumphExtension {
        this.platformsData["version"] = version
        return this
    }

    fun cloud(cloudData: Map<String, Any>): TriumphExtension {
        if (cloudData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `core` doesn't have version")
        }

        this.cloudData.putAll(cloudData)
        return this
    }

    fun cloud(version: String): TriumphExtension {
        this.cloudData["version"] = version
        return this
    }

    fun papi(papiData: Map<String, String>): TriumphExtension {
        if (papiData["version"] == null) {
            throw RequiredValueNotFoundException("Dependency `core` doesn't have version")
        }

        this.papiData.putAll(papiData)
        return this
    }

    fun papi(version: String): TriumphExtension {
        this.papiData["version"] = version
        return this
    }

}