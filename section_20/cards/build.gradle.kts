plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.jib) // Gradle automatically generates type-safe accessors from your libs.versions.toml file. The plugin IDs defined under [plugins]
}

group = "com.bankdemo.cards"
version = "1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

// jib if you want to push the image to a register like Docker Hub
// jibDockerBuild to build the image locally using the docker command
jib {
    dockerClient {
        executable = "/usr/local/bin/docker"
    }
    to {
        // run the command to generate docker image locally: $ ./gradlew jibDockerBuild
        image = "adrianjpbv/${project.name}:s20"
        auth {
            username = System.getenv("DOCKER_USERNAME") ?: project.findProperty("docker.username") as String?
            password = System.getenv("DOCKER_PASSWORD") ?: project.findProperty("docker.password") as String?
        }
    }
}

dependencies {
    // BOM project
    implementation(platform("com.bankdemo:eazy-bom:1.0"))

    // Local dependencies
    implementation(libs.bankdemo.common)

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Cloud Client
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    // Eureka Discovery Client
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Swagger
    implementation(libs.springdoc.openapi)

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Micrometer and Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // OpenTelemetry
    runtimeOnly(libs.open.telemetry)

    // H2
    runtimeOnly("com.h2database:h2")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.springCloudVersion.get()}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
