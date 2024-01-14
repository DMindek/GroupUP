pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GroupUP_app"
include(":app")
include(":core:ui")
include(":feature:auth")
include(":data:user")
include(":core:utils")
include(":core:utils:location")
include(":network")
include(":feature:event")
include(":data:event")
include(":core:utils:location:OSMservice")
include(":core:utils:location:GoogleMapsService")
