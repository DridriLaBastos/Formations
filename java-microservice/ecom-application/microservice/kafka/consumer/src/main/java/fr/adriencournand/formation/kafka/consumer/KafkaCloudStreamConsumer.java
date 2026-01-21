package fr.adriencournand.formation.kafka.consumer;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaCloudStreamConsumer {

    @Bean
    public Consumer<RiderLocation> ProcessRiderLocation() {
        return location -> {
            System.out.println("Received" + location.toString());
        };
    }
}
