# Step 1: Build the app using Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the app using JDK base image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR built in the first stage
COPY --from=build /app/target/habitup-0.0.1-SNAPSHOT.jar app.jar

# Use the secret application.properties file
ENV SPRING_CONFIG_LOCATION=/etc/secrets/application.properties

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
