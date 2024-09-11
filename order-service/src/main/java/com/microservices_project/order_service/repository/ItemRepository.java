package com.microservices_project.order_service.repository;

import com.microservices_project.order_service.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository  extends JpaRepository<Items, Long> {
    Optional<Items> findBySkuCode(String skuCode);
}
