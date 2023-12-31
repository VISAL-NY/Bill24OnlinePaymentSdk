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
        maven {setUrl("https://jitpack.io") }
        jcenter()
    }
}

rootProject.name = "Bill24OnlinePaymentSdk"
include(":app")
include(":OnlinePaymentSdk")
