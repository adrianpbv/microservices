plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.jib) // Gradle automatically generates type-safe accessors from your libs.versions.toml file. The plugin IDs defined under [plugins]
}

group = "com.bankdemo"
version = "1.0"
description = "gateway-server"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
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
        image = "adrianjpbv/${project.name}:s20" // image name will be adrianjpbv/gateway-server:s8
        auth {
            username = System.getenv("DOCKER_USERNAME") ?: project.findProperty("docker.username") as String?
            password = System.getenv("DOCKER_PASSWORD") ?: project.findProperty("docker.password") as String?
        }
    }
}

dependencies {
    // BOM project
    implementation(platform("com.bankdemo:eazy-bom:1.0"))
    // Spring Cloud Config Client
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    // Spring Actuator
	implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Reactive Spring Cloud Gateway
	implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")

    // Circuit Breaker
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

    // Eureka Discovery Client
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // Micrometer and Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // OpenTelemetry
    runtimeOnly(libs.open.telemetry)

    // Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
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
