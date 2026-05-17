rootProject.name = "eazy-bom"

// TODO 6 include the common module build
if (file("common").exists()) {
    includeBuild("common")
}