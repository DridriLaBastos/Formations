package fr.adriencournand.formation.notification;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void HandleOrderEvent(Map<String, Object> orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);

        Long orderId = Long.valueOf(orderEvent.get("orderId").toString());
        String status = orderEvent.get("status").toString();

        System.out.println("Received Order Event : {id: " + orderId + ", " + status + "}");

        // Update database
        // Send Notification
        // Send Emails
        // Generate Invoice
        // Send Seller Notification

    }
}
