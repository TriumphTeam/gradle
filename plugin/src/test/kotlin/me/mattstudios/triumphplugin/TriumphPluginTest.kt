package me.mattstudios.triumphplugin

import io.kotlintest.shouldNotBe
import io.kotlintest.specs.WordSpec
import org.gradle.testfixtures.ProjectBuilder

/**
 * @author Matt
 */
class TriumphPluginTest : WordSpec ({

    "Using the Plugin ID" should {
        "Apply the Plugin" {
            val project = ProjectBuilder.builder().build()
            project.pluginManager.apply("me.mattstudios.triumph")

            project.plugins.getPlugin(TriumphPlugin::class.java) shouldNotBe null
        }
    }

})
