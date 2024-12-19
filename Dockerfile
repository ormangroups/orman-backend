# Use Maven with OpenJDK 17 for the build stage
FROM maven:3.8.6-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests to speed up the build process
RUN mvn clean package -DskipTests

# Use OpenJDK 17 runtime for the final stage
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
