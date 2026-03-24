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

rootProject.name = "LotteMartSuperApp"
include(":app")
include(":core:common")
include(":core:network")
include(":core:database")
include(":core:datastore")
include(":core:model")
include(":core:designsystem")

include(":features:auth")
include(":features:home")
include(":features:cart")
include(":features:checkout")
include(":features:employee:barcode_scanner")
include(":features:employee:shipper_tracking")
include(":features:employee:hr_manager")
include(":features:admin")