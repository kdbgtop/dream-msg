pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "dream-msg-master"

include(":plugin-core")
include(":plugin-core:nms:api")