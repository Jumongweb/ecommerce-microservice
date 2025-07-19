FROM maven:3.9.11-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY ./pom.xml .
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
EXPOSE 8002
ENTRYPOINT ["java", "-Dmicrometer.insights.metrics.jvm.cgroup.enabled=false", "-jar", "order-service.jar"]