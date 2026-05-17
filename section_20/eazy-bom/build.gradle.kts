plugins {
    `java-platform` // TODO 1
} // a platform describes versions not what gets pulled in

group = "com.bankdemo"
version = "1.0"
description = "Common BOM for eazybank microservices"

repositories {
    mavenCentral()
}

// TODO 2
javaPlatform {
    allowDependencies()
}

dependencies {
    // TODO 3 Re-exports the imported BOM to consumers of your BOM
    api(platform(libs.spring.boot.bom))
    api(platform(libs.spring.cloud.bom))
    api(platform(libs.micrometer.bom))

    // import these libraries to the platform
    constraints {
        api(libs.springdoc.openapi)
        api(libs.lombok)
        api(libs.h2)
        api(libs.bankdemo.common) // TODO 7 local shared library
        runtime(libs.spring.boot.starter.test) // springboot test doesn't belong to a bom
        runtime(libs.open.telemetry)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
