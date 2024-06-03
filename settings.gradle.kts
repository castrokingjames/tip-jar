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

rootProject.name = "tip-jar"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":tipjar")
include(":timber")
include(":ui:common")
include(":ui:compose")
include(":ui:viewmodel")
include(":di:annotation")
include(":model")
include(":domain")
include(":data")
include(":datasource:local")
include(":feature:home")
include(":feature:history")
