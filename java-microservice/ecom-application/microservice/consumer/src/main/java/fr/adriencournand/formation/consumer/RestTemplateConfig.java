package fr.adriencournand.formation.consumer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @Primary // Using Primary here because there are 2 instances of RestTemplate as Bean and
             // this one is needed to be injected to the function required a non @Qualified
             // bean as parameter
    @LoadBalanced
    public RestTemplate RestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
