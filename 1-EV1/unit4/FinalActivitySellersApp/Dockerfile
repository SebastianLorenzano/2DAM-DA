# Use an official OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY SellersApp-RestApi/target/SellersApp-RestApi-0.0.1-SNAPSHOT.jar app.jar

# Expose HTTPS port (default 8443)
EXPOSE 8443

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
