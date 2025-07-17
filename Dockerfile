# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR into the container
COPY target/filent-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Spring Boot runs on 8080 by default)
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
