# Use Maven with OpenJDK 8 for building the application
FROM maven:3.8.8-openjdk-8 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Use a lightweight JDK 8 image for running the application
FROM openjdk:8-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/Hotel-Managment-0.0.1-SNAPSHOT.jar Hotel-Management.jar

# Expose the port your application will run on
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "Hotel-Management.jar"]
