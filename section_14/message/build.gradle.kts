plugins {
    java
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "com.bankdemo"
version = "1.0"
description = "message"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
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

extra["springCloudVersion"] = "2025.1.0"

jib {
    dockerClient {
        executable = "/usr/local/bin/docker"
    }
    to {
        // run the command: $ ./gradlew jibDockerBuild
        image = "adrianjpbv/${project.name}:s14" // image name will be adrianjpbv/accounts:s8
        auth {
            username = System.getenv("DOCKER_USERNAME") ?: project.findProperty("docker.username") as String?
            password = System.getenv("DOCKER_PASSWORD") ?: project.findProperty("docker.password") as String?
        }
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-stream")
    // TODO 1 Kafka
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // SCS Test
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
