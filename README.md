# MicroservicesSpringBoot

Spring Boot Application with Products/Orders/Inventory functionality implemented as Microservices. 

Uses Postgres Database(product-service) and MySQL Database(order-database, inventory-database)

Microservices register as Discovery Clients with Eureka Server. 

API Gateway does load balancing and controls the end point to the app http://localhost:8080/api/order
