# Use Eclipse Temurin Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set work directory
WORKDIR /app

# Copy the built JAR into the image
COPY target/habitup-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
