package fr.adriencournand.formation.consumer.restclient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @LoadBalanced
    @Primary // Using Primary here because there are 2 instances of RestTemplate as Bean and
             // this one is needed to be injected to the function required a non @Qualified
             // bean as parameter
    public RestClient.Builder LoadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient RestClient(RestClient.Builder builder) {
        return builder.baseUrl("http://provider")
                .build();
    }

}
