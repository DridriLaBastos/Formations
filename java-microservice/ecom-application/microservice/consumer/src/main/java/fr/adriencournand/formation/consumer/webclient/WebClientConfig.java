package fr.adriencournand.formation.consumer.webclient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    @Primary // Using Primary here because there are 2 instances of RestTemplate as Bean and
             // this one is needed to be injected to the function required a non @Qualified
             // bean as parameter
    public WebClient.Builder LoadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient WebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://provider").build();
    }

}
