package fr.adriencournand.formation.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator GetCustomRouteLocator(RouteLocatorBuilder builder) {
        // return builder.routes()
        //             .route("user-service", r -> r.path("/users/**")
        //             .filters(f -> f.rewritePath("/users(?<segment>/?.*)", "/api/users${segment}"))
        //             .uri("lb://ecom-user-microservice"))
        //             .route("order-service", r -> r.path("/api/orders/**", "/api/cart/**")
        //             .uri("lb://ecom-order-microservice"))
        //             .route("eureka-server", r -> r.path("/eureka/main")
        //             .filters(f -> f.rewritePath("/eureka/main", "/"))
        //             .uri("http://localhost:8761"))
        //             .build();

        return builder.routes().route("product-service", r -> r.path("/api/products/**").filters(f -> f.circuitBreaker(config -> config.setName("gatewayBreaker").setFallbackUri("forward:/fallback/products"))).uri("lb://ecom-product-microservice")).build();
    }

}
