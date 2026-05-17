rootProject.name = "config-server"

if (file("../eazy-bom").exists()) {
    includeBuild("../eazy-bom")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../eazy-bom/gradle/libs.versions.toml"))
        }
    }
}