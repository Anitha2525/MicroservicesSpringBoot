package com.microservices_project.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		// System.out.println("HELLO ANITHA");
	}

}

//Inter Process Communication with Synchronous Communication - no hard coded url
// Microservices will be deployed in cloud with dynamic ip address - also multiple instances of same microservice
// Service Discovery Pattern - Discovery Server (maintains all ip addresses of all services
// ALL Microservices will register with the Discovery Server
// Discovery Server sends the Registry to Client
// Discovery Server using Netflix Eureka
//Service Discovery : Eureka instances can be registered and clients can discover
// the instances using Spring-managed beans
