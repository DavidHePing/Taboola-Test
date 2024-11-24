# Build Stage
FROM maven:3.9.9-amazoncorretto-8-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests spring-boot:repackage

# Run Stage
FROM amazoncorretto:8-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
