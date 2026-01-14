package fr.adriencournand.formation.ecom.service.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom.service.order.dto.OrderItemDTO;
import fr.adriencournand.formation.ecom.service.order.dto.OrderResponse;
import fr.adriencournand.formation.ecom.service.order.model.CartItem;
import fr.adriencournand.formation.ecom.service.order.model.EOrderStatus;
import fr.adriencournand.formation.ecom.service.order.model.Order;
import fr.adriencournand.formation.ecom.service.order.model.OrderItem;
import fr.adriencournand.formation.ecom.service.order.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final IOrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public Optional<OrderResponse> CreateOrder(String userId) {
        // Validate for cart items
        List<CartItem> cartItems = cartService.CartForUser(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
        // Validate for user
        // Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        // if (userOptional.isEmpty()) {
        // Optional.empty();
        // }

        // User user = userOptional.get();

        // Calculate total price
        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(EOrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(null, item.getProductId(), item.getQuantity(), item.getPrice(), order))
                .collect(Collectors.toList());
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cartService.ClearCart(userId);

        rabbitTemplate.convertAndSend(exchangeName, routingKey,
                Map.of("orderId", savedOrder.getId(), "status", "CREATED"));

        OrderResponse response = MapOrderToOrderResponse(savedOrder);

        return Optional.of(response);
    }

    static private OrderResponse MapOrderToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getTotalAmount(), order.getStatus(), order.getItems().stream()
                .map(orderItem -> new OrderItemDTO(orderItem.getId(), orderItem.getProductId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(), orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))))
                .toList(), order.getCreatedAt());
    }

}
