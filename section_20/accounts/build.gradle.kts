plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.jib) // Gradle automatically generates type-safe accessors from your libs.versions.toml file. The plugin IDs defined under [plugins]
}

group = "com.bankdemo"
version = "1.0"
description = "Microservice for Accounts"


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

jib {
    dockerClient {
        executable = "/usr/local/bin/docker"
    }
    to {
        // run the command: $ ./gradlew jibDockerBuild
        image = "adrianjpbv/${project.name}:s20" // image name will be adrianjpbv/accounts:s8
        auth {
            username = System.getenv("DOCKER_USERNAME") ?: project.findProperty("docker.username") as String?
            password = System.getenv("DOCKER_PASSWORD") ?: project.findProperty("docker.password") as String?
        }
    }
}

dependencies {
    // TODO 5 import the BOM as a platform dependency
    // BOM project
    implementation(platform("com.bankdemo:eazy-bom:1.0")) // TODO 8 import common module

    implementation(libs.bankdemo.common)

    // TODO 6 Import dependecies trhough the BOM project
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Cloud Client
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    // Eureka Discovery Client
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Circuit Breaker
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

    // Open Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // Swagger - version comes from BOM
    implementation(libs.springdoc.openapi)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Micrometer and Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // OpenTelemetry
    runtimeOnly(libs.open.telemetry)

    implementation("org.springframework.cloud:spring-cloud-stream")
    // Add Kafka instead of RabbitMQ
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

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
