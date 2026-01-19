package fr.adriencournand.formation.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void Listen(String message) {
        System.out.println("Received message : '" + message + "'");
    }
}
