import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30-RC"
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.14.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.mattstudios"
version = "0.3.4"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.triumphteam.dev/releases/")
    maven("https://repo.triumphteam.dev/snapshots/")
    maven("https://jitpack.io")
}

val shadowImplementation: Configuration by configurations.creating
configurations["compileOnly"].extendsFrom(shadowImplementation)
configurations["testImplementation"].extendsFrom(shadowImplementation)

dependencies {
    shadowImplementation(kotlin("stdlib"))
    shadowImplementation("org.ow2.asm:asm:9.3")
    shadowImplementation("org.ow2.asm:asm-tree:9.3")
    shadowImplementation("org.ow2.asm:asm-util:9.3")
    shadowImplementation("org.ow2.asm:asm-commons:9.3")
    shadowImplementation("com.google.code.gson:gson:2.8.6")

    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.10")

    shadowImplementation("dev.triumphteam:triumph-gradle-annotations:0.0.1")
    shadowImplementation("dev.triumphteam:nebula-bukkit:1.0.0-SNAPSHOT")
    shadowImplementation("org.yaml:snakeyaml:1.29")
}

val shadowJarTask = tasks.named("shadowJar", ShadowJar::class.java)
val relocateShadowJar = tasks.register("relocateShadowJar", ConfigureShadowRelocation::class.java) {
    target = shadowJarTask.get()
}

shadowJarTask.configure {
    dependsOn(relocateShadowJar)
    archiveClassifier.set("")
    configurations = listOf(shadowImplementation)
}

// Required for plugin substitution to work in samples project
artifacts {
    add("runtimeOnly", shadowJarTask)
}

tasks.whenTaskAdded {
    if (name == "publishPluginJar" || name == "generateMetadataFileForPluginMavenPublication") {
        dependsOn(tasks.named("shadowJar"))
    }
}

// Disabling default jar task as it is overridden by shadowJar
tasks.named("jar").configure {
    enabled = false
}

val ensureDependenciesAreInlined by tasks.registering {
    description = "Ensures all declared dependencies are inlined into shadowed jar"
    group = HelpTasksPlugin.HELP_GROUP
    dependsOn(tasks.shadowJar)

    doLast {
        val nonInlinedDependencies = mutableListOf<String>()
        zipTree(tasks.shadowJar.flatMap { it.archiveFile }).visit {
            if (!isDirectory) {
                val path = relativePath
                if (
                    !path.startsWith("META-INF") &&
                    path.lastName.endsWith(".class") &&
                    !path.pathString.startsWith(
                        "me.mattstudios.triumph".replace(".", "/")
                    )
                ) {
                    nonInlinedDependencies.add(path.pathString)
                }
            }
        }
        if (nonInlinedDependencies.isNotEmpty()) {
            throw GradleException("Found non inlined dependencies: $nonInlinedDependencies")
        }
    }
}

tasks.named("check") {
    dependsOn(ensureDependenciesAreInlined)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

    withType<GenerateModuleMetadata> {
        enabled = false
    }

    withType<ShadowJar> {
        mapOf(
            "com.google" to "google",
            "org.yaml" to "yaml",
            "org.objectweb.asm" to "asm"
        ).forEach { relocate(it.key, "dev.triumphteam.lib.${it.value}") }

        // Hacky trick to prevent kotlin from being relocated to `shadow.kotlin`
        relocate("kotlin", "kotlin")
    }

    test {
        useJUnitPlatform()
    }
}

// Work around publishing shadow jars
afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                if (name == "pluginMaven") {
                    setArtifacts(listOf(shadowJarTask.get()))
                }

                repositories {
                    maven {
                        name = "localPluginRepository"
                        url = uri("C:/Users/xpsyk/OneDrive/Documents/test")
                    }
                }
            }
        }
    }
}

gradlePlugin {
    plugins {
        create("triumph") {
            id = "me.mattstudios.triumph"
            displayName = "Triumph Gradle"
            description = "Plugin with utilities for the Triumph projects."
            implementationClass = "dev.triumphteam.TriumphPlugin"
        }
    }
}

pluginBundle {
    website = "https://mf.mattstudios.me"
    vcsUrl = "https://github.com/TriumphDev/gradle"
    tags = listOf("spigot", "bukkit", "minecraft")
}
