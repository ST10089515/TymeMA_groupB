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
        mavenCentral() // For ImagePicker library, this line is enough. Although, it has been published on jitpack as well
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "TymeMA_V1"
include(":app")
 