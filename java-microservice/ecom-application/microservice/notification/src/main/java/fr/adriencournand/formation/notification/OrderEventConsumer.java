package fr.adriencournand.formation.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void HandleOrderEvent(OrderCreatedEvent orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);

        Long orderId = orderEvent.getOrderId();
        EOrderStatus status = orderEvent.getOrderStatus();

        System.out.println("Received Order Event : {id: " + orderId + ", " + status + "}");

        // Update database
        // Send Notification
        // Send Emails
        // Generate Invoice
        // Send Seller Notification

    }
}
