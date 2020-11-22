package me.mattstudios.testing

import me.mattstudios.annotations.BukkitPlugin
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin

@BukkitPlugin
class MainClass : JavaPlugin() {

    override fun onEnable() {
        println("fuck")
    }

}