package fr.adriencournand.formation.kafka.Producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Producer {

    private final KafkaTemplate<String, RiderLocation> kafkaTemplate;

    public Producer(KafkaTemplate<String, RiderLocation> template) {
        this.kafkaTemplate = template;
    }

    @PostMapping("/send")
    public String SendMessage() {
        RiderLocation location = new RiderLocation("rider123", 28.61, 77.23);
        kafkaTemplate.send("java-created-topic", location);
        return "Location sent to java-created-topic: '" + location.toString() + "'";
    }
}
