#Start with a base image containing Java runtime
FROM amazoncorretto:21.0.5 as build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

# Production stage
FROM openjdk:21-jdk-slim
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="adrianjpbv"

# Add the application's jar to the image
COPY --from=build /app/build/libs/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]