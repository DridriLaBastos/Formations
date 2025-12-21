package fr.adriencournand.formation.ecom_application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import fr.adriencournand.formation.ecom_application.model.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalamount;
    private EOrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
}
