# Use lightweight Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory in container
WORKDIR /app

# Copy the built JAR from your Maven target folder
COPY target/habitup-0.0.1-SNAPSHOT.jar app.jar

# Expose the port used by the Spring Boot app
EXPOSE 8080

# Use external application.properties mounted by Render
ENV SPRING_CONFIG_LOCATION=/etc/secrets/application.properties

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
