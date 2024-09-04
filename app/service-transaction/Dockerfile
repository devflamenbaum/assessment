# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17 AS build
LABEL authors="Gabriel"

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and any other required files
COPY pom.xml ./

# Download the dependencies
RUN mvn dependency:go-offline

# Copy the rest of the application source code
COPY src ./src

# Package the application
RUN mvn package

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the first stage
COPY --from=build /app/target/service-transaction.jar /app/service-transaction.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENV DB_USERNAME=pismouser
ENV DB_PASSWORD=pismopass

# Expose the port that the application will run on
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/service-transaction.jar"]