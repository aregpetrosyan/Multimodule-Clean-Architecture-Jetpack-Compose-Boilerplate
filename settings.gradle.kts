pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Multimodule-Clean-Architecure-Jetpack-Compose"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:network")
include(":feature:explore")
include(":feature:random")
include(":core:analytics")
include(":feature:favorites")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:datastore")
include(":feature:similar")
include(":core:navigation")
include(":feature:favorites:api")
