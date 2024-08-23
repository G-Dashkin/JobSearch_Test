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

rootProject.name = "JobSearch_Test"

include(":app")
include(":core:ui")
include(":core:navigation")
include(":core:utils")
include(":data_api")
include(":domain_models")

include(":features:entrance")
include(":features:entrance_api")
include(":features:main")
include(":features:main_api")
include(":data")
