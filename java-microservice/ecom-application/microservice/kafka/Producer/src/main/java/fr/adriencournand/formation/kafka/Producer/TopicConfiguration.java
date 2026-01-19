package fr.adriencournand.formation.kafka.Producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {

    @Bean
    public NewTopic CreateTopic() {
        return new NewTopic("java-created-topic", 3, (short) 1);
    }
}
