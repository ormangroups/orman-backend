# Stage 1: Build the application using Maven and OpenJDK 17
FROM maven:3.9.4-openjdk-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the project, skipping tests for faster builds
RUN mvn clean package -DskipTests

# Stage 2: Use a lightweight OpenJDK 17 image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/orman-0.0.1-SNAPSHOT.jar orman.jar

# Expose the port your application runs on (default 8080 for Spring Boot)
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "orman.jar"]
