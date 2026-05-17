rootProject.name = "accounts"

// TODO 4 include BOM module
if (file("../eazy-bom").exists()) {
    includeBuild("../eazy-bom")
}

// TODO 4.1 setup the version catalog
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../eazy-bom/gradle/libs.versions.toml"))
        }
    }
}