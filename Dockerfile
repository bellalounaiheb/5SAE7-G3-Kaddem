# Use an official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the JAR name as a build argument
ARG JAR_FILE=target/app.jar

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container (update 'your-application.jar' with the actual name)
COPY ${JAR_FILE} app.jar

# Expose the application's port (replace 8080 with your app’s port if different)
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]