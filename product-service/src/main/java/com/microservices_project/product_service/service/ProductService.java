package com.microservices_project.product_service.service;

import com.microservices_project.product_service.dto.ProductRequest;
import com.microservices_project.product_service.dto.ProductResponse;
import com.microservices_project.product_service.model.Product;
import com.microservices_project.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product(
                productRequest.getName(),
                productRequest.getDescription(),
                productRequest.getPrice());
        productRepository.save(product);
        //log.info("Product is saved"); Post call localhost:8083/api/product
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product ->
        {
            return new ProductResponse(product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice());
        }).toList();
        // Get call localhost:8083/api/product
    }

    public List<String> getAvailableProducts(){
        List<String> productNames = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        products.forEach(product ->
                productNames.add(product.getName()));
        return productNames;
    }
}
    //By using generics, programmers can implement generic algorithms
    // that work on collections of different types, can be customized,
    // and are type safe and easier to read.
    /*
    Approach 5: Specify Search Criteria Code with a Lambda Expression
    The CheckPerson interface is a functional interface. A functional
    interface is any interface that contains only one abstract method.
    (A functional interface may contain one or more default methods or
    static methods.) Because a functional interface contains only one
    abstract method, you can omit the name of that method when you
    implement it. To do this, instead of using an anonymous class
    expression, you use a lambda expression, which is highlighted in
    the following method invocation:

    printPersons(
        roster,
            (Person p) -> p.getAge() >= 18
        && p.getAge() <= 25
    );
     */

