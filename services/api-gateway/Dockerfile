FROM maven:3.9.11-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY ./pom.xml .
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8005
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]