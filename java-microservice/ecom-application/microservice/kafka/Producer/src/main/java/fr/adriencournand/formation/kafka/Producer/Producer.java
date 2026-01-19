package fr.adriencournand.formation.kafka.Producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> template) {
        this.kafkaTemplate = template;
    }

    @PostMapping("/send")
    public String SendMessage(@RequestParam String message) {
        kafkaTemplate.send("my-topic", message);
        return "Message sent to my-topic: '" + message + "'";
    }
}
