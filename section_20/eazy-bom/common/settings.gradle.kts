rootProject.name = "common"

if (file("../").exists()) {
    includeBuild("../")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}