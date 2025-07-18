Multi-Service E-Commerce Backend

A simple microservices-based backend for an e-commerce application using Spring Boot. The system is composed of:

- Product Service: Manages product details.
- Order Service: Handles customer orders.
- API Gateway: Acts as a single entry point for all external requests.
- Eureka Service Registry: Enables service discovery and registration.
- Load Balancer: Distributes requests across multiple instances of services.


ecommerce-backend/
└──service
    ├── api-gateway/
    ├── eureka-server/
    ├── product-service/
    ├── order-service/
└── postmanDocumentationLink
└── README.md


How to Run the Project

1. Clone the Repository

```bash
git clone https://github.com/Jumongweb/ecommerce-microservice.git
cd ecommerce-microservice
cd services

Instance 1:
cd eureka-server
./mvnw spring-boot:run
Make sure it's running at: http://localhost:8761

Instance 2
cd product-service
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081

Instance 3
cd order-service
./mvnw spring-boot:run

cd api-gateway
./mvnw spring-boot:run

Access through api gateway:
    http://localhost:8005/api/v1/products
    http://localhost:8005/api/v1/orders

Postman documentation link:
    https://documenter.getpostman.com/view/33710179/2sB34kCy3R
