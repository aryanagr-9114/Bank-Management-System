# Step 1: Use an official Java 17 environment as our base "Image"
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Create a directory inside the container for our app to live
WORKDIR /app

# Step 3: Copy the compiled Java .jar file from our computer into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Tell Docker which port the app runs on (Spring Boot uses 8080 by default)
EXPOSE 8080

# Step 5: The command that executes when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
