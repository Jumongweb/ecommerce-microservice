FROM maven:3.8-jdk-17-slim AS builder
WORKDIR /app
COPY ./pom.xml .
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/product-service-0.0.1-SNAPSHOT.jar product-service.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "product-service.jar"]