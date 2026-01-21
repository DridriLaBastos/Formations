package fr.adriencournand.formation.kafka.Producer;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaCloudStreamProducer {

    @Bean
    public Supplier<RiderLocation> SendRiderLocation() {
        return () -> {
            Random r = new Random();
            String riderId = "rider" + r.nextInt(20);
            RiderLocation location = new RiderLocation(riderId, 22.48, 73.22);
            System.out.println("Sending: " + location.toString());
            return location;
        };
    }

}
