# Stage 1: Build the application using Maven and OpenJDK 17
FROM maven:3.8.6-openjdk-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the project, skipping tests for faster builds
RUN mvn clean package -DskipTests

# List files in the target directory to check if the JAR was created
RUN ls -l /app/target/

# Stage 2: Use a lightweight OpenJDK 17 image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage to the final image
COPY --from=builder /app/target/orman-backend-0.0.1-SNAPSHOT.jar /app/orman-backend.jar

# Expose the port your application runs on (default 8080 for Spring Boot)
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app/orman-backend.jar"]
