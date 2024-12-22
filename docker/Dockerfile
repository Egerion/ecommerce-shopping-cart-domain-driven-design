# Use Maven image to build the project
FROM maven:3.8.1-openjdk-17-slim AS build

# Set working directory
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the project
RUN mvn clean install

# Use OpenJDK runtime image to run the app
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy JAR files from the build stage
COPY --from=build /app/infrastructure/target/infrastructure-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

ENV SPRING_PROFILES_ACTIVE=docker

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]