# Use an official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the JAR name as a build argument, defaulting to 'target/app.jar'
ARG JAR_FILE=target/kaddem-1.0.jar

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose the application's port (replace 8082 with your app’s port if different)
EXPOSE 8089

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
