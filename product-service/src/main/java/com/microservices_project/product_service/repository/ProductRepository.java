package com.microservices_project.product_service.repository;

import com.microservices_project.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
