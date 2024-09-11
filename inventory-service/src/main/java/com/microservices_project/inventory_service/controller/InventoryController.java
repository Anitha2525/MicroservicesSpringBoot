package com.microservices_project.inventory_service.controller;

import com.microservices_project.inventory_service.dto.InventoryResponse;
import com.microservices_project.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    // http://localhost:8082/api/inventory/COACH,kATE-SPADE path variable
    // http://localhost:8082/api/inventory/skuCode=COACH&skuCode=KATESPADE request parameter
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        System.out.println("from ISINSTOCK method");
        return inventoryService.isInStock(skuCode);
    }

}
