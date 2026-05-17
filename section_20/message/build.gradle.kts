plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.jib) // Gradle automatically generates type-safe accessors from your libs.versions.toml file. The plugin IDs defined under [plugins]
}

group = "com.bankdemo"
version = "1.0"
description = "message"

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
    implementation(platform("com.bankdemo:eazy-bom:1.0"))
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-stream")

    // Kafka
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // SCS Test
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
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
