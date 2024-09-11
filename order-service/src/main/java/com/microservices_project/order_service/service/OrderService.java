package com.microservices_project.order_service.service;

import com.microservices_project.order_service.dto.InventoryResponse;
import com.microservices_project.order_service.dto.ItemsDto;
import com.microservices_project.order_service.dto.OrderRequest;
import com.microservices_project.order_service.model.Items;
import com.microservices_project.order_service.model.Order;
import com.microservices_project.order_service.repository.ItemRepository;
import com.microservices_project.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private DiscoveryClient discoveryClient;

    // constructor injection
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final WebClient.Builder webClientBuilder;

    // end point is Post call http://localhost:8081/api/order
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<Items> items = orderRequest.getItemsDtoList()
                .stream()
                .map(itemsDtoList -> mapToItems(itemsDtoList))
                .toList();
        order.setItemsList(items);

        List<String> skuCodes = order.getItemsList()
                .stream().map(itemsList -> itemsList.getSkuCode()).toList();

        // Using WebClient call Microservice-Inventory-service and place order if product is available
        // change - hardcoded ipaddress localhost:8082 of inventory-service to be a true discovery client
        //uri("http://localhost:8082/api/inventory?skuCode=COACH&skuCode=kate spade")

        List<String> productNames = webClientBuilder.build()
                .get().uri("http://product-service/api/product/showAvailableProducts")
                .retrieve().bodyToMono(List.class).block();

        boolean placeOrder = skuCodes.stream().allMatch(element -> productNames.contains(element));
        if (placeOrder) {

            InventoryResponse[] inventoryResponses = webClientBuilder.build()
                    .get().uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .accept(MediaType.ALL)
                    .retrieve().bodyToMono(InventoryResponse[].class).block();

            boolean allProductsInStock = Arrays.stream(inventoryResponses)
                    .allMatch(eachInventoryResponse -> eachInventoryResponse.isInStock());
            // Java 8 METHOD

            if (allProductsInStock)
                orderRepository.save(order);
            else
                throw new IllegalArgumentException("Product is not in stock...");
        }else{
            System.out.println("List of skuCodes are :");
            skuCodes.forEach(element -> System.out.println(element + ","));
            System.out.println("List of productNames are :");
            productNames.forEach(element -> System.out.println(element + ","));
            throw new IllegalArgumentException("Products in the Order are not Listed in the Product Service Database");
        }
    }

    private Items mapToItems(ItemsDto itemsDtoList) {
        Items items = new Items();
        items.setSkuCode(itemsDtoList.getSkuCode());
        items.setPrice(itemsDtoList.getPrice());
        items.setQuantity(itemsDtoList.getQuantity());
        return items;
    }

    public Integer getInventory(String skuCode) {
        Items item = itemRepository.findBySkuCode(skuCode).get();
        return item.getQuantity();
    }

    /* For Debug Purpose
        List<String> services = discoveryClient.getServices();
        services.forEach(service -> {
            System.out.println(service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            instances.forEach(instance ->
                    System.out.println("HOST:" + instance.getHost() +"  URI :"
                            +instance.getUri()+ "   Port :"+ instance.getPort() + " instanceId :" +instance.getInstanceId()));
        }); */
       /* {
    "itemsDtoList":[
        {
            "skuCode":"Tony Birch",
            "price":60,
            "quantity":5
        },
        {
            "skuCode":"COACH",
            "price":1200,
            "quantity":2
        },
        {
            "skuCode":"FOSSIL WATCH",
            "price":1200,
            "quantity":2
        }
       ]
    }*/
}
