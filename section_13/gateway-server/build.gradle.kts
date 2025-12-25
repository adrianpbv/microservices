plugins {
	java
    id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "com.bankdemo"
version = "1.0"
description = "gateway-server"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
        image = "adrianjpbv/${project.name}:s13" // image name will be adrianjpbv/gateway-server:s8
        auth {
            username = System.getenv("DOCKER_USERNAME") ?: project.findProperty("docker.username") as String?
            password = System.getenv("DOCKER_PASSWORD") ?: project.findProperty("docker.password") as String?
        }
    }
}

dependencies {
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
    implementation("io.micrometer:micrometer-bom:1.15.4")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // OpenTelemetry
    runtimeOnly("io.opentelemetry.javaagent:opentelemetry-javaagent:2.21.0")

    // Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
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
