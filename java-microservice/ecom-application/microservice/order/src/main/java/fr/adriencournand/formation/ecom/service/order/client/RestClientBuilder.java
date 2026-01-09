package fr.adriencournand.formation.ecom.service.order.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientBuilder {

    @Bean
    @LoadBalanced
    public RestClient.Builder GetBuilder() {
        return RestClient.builder();
    }

}
