package fr.adriencournand.formation.ecom_application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fr.adriencournand.formation.ecom_application.dto.OrderItemDTO;
import fr.adriencournand.formation.ecom_application.dto.OrderResponse;
import fr.adriencournand.formation.ecom_application.model.CartItem;
import fr.adriencournand.formation.ecom_application.model.EOrderStatus;
import fr.adriencournand.formation.ecom_application.model.Order;
import fr.adriencournand.formation.ecom_application.model.OrderItem;
import fr.adriencournand.formation.ecom_application.model.User;
import fr.adriencournand.formation.ecom_application.repository.IOrderRepository;
import fr.adriencournand.formation.ecom_application.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final IUserRepository userRepository;
    private final IOrderRepository orderRepository;

    public Optional<OrderResponse> CreateOrder(String userId) {
        // Validate for cart items
        List<CartItem> cartItems = cartService.CartForUser(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
        // Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            Optional.empty();
        }

        User user = userOptional.get();

        // Calculate total price
        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(EOrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(null, item.getProduct(), item.getQuantity(), item.getPrice(), order))
                .collect(Collectors.toList());
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cartService.ClearCart(userId);

        OrderResponse response = MapOrderToOrderResponse(savedOrder);

        return Optional.of(response);
    }

    static private OrderResponse MapOrderToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getTotalAmount(), order.getStatus(), order.getItems().stream()
                .map(orderItem -> new OrderItemDTO(orderItem.getId(), orderItem.getProduct().getId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(), orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))))
                .toList(), order.getCreatedAt());
    }

}
