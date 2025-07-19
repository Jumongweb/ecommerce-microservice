FROM maven:3.9.11-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY ./pom.xml .
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/product-service-0.0.1-SNAPSHOT.jar product-service.jar
EXPOSE 8001
ENTRYPOINT ["java", "-Dmicrometer.insights.metrics.jvm.cgroup.enabled=false", "-jar", "product-service.jar"]