package com.microservices_project.order_service.repository;

import com.microservices_project.order_service.model.Items;
import com.microservices_project.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long>{

}
