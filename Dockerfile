FROM maven:3.8.4-openjdk-17 as builder

# Set working directory
WORKDIR /app

# Copy environment file
COPY .env /app/.env

# Copy application source code
COPY . /app/.

# Build application
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy environment file
COPY --from=builder /app/.env /app/.env

# Copy built JAR file from builder stage
COPY --from=builder /app/target/*.jar /app/*.jar

# Expose port
EXPOSE 8080

# Set entrypoint
ENTRYPOINT ["java", "-jar", "/app/*.jar"]