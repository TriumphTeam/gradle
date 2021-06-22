rootProject.name = "triumph-gradle-plugin"

listOf("plugin", "annotations", "plugin-test").forEach {
    include(it)
    findProject(":$it")?.name = "triumph-gradle-$it"
}