package fr.adriencournand.formation.ecom.service.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import fr.adriencournand.formation.ecom.service.order.model.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateEvent {
    private Long orderId;
    private String userId;
    private EOrderStatus orderStatus;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
