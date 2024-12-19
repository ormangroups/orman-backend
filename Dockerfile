# Stage 1: Build the application
FROM maven:3.8.6-openjdk-11 AS builder

# Explicitly set JAVA_HOME for Maven
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Verify Java and Maven versions
RUN java -version && mvn -version

# Set the working directory
WORKDIR /app

# Copy your Maven project's pom.xml and source code to the container
COPY pom.xml ./
COPY src ./src

# Build the application, explicitly setting JAVA_HOME
RUN mvn clean package -DskipTests

# Verify the build output
RUN ls -l /app/target/

# Stage 2: Use a lightweight OpenJDK 17 runtime for the final image
FROM openjdk:17-jdk-slim

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Copy the JAR file from the builder stage to the final image
COPY --from=builder /app/target/orman-0.0.1-SNAPSHOT.jar /app/orman.jar

# Set the entry point for the final image
ENTRYPOINT ["java", "-jar", "/app/orman.jar"]
