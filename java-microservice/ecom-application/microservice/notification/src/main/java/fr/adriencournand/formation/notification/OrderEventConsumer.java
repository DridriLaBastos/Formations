package fr.adriencournand.formation.notification;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderEventConsumer {
    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Received order created event for oder: {}", event.getOrderId());
            log.info("Received order created event for user is: {}", event.getUserId());
        };
    }
}
