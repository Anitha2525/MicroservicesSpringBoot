package com.microservices_project.order_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
        // automatically create Client Side LoadBalancing - it
        // will call Inventory Service one after another WebClient.builder
    }
}
//You can use the @Bean annotation in a @Configuration or in a @Component annotated class.
//@Bean is a method-level annotation and a direct analog of the XML <bean/> element.
// The annotation supports some of the attributes offered by <bean/>
//By default, the bean name is the same as the method name. This method is used to register a
// bean definition within an ApplicationContext of the type specified as the method’s return value.
// The default scope is singleton.
//The idea, is to let the container handle the instantiation of your logic or services that change regularly
// so you can swap them easily without having to open the client classes, because these might be
// already in production, tested and a developer could potentially introduce new bugs and break things.