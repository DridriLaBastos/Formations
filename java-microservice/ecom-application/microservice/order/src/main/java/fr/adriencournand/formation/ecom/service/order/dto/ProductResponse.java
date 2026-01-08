package fr.adriencournand.formation.ecom.service.order.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private boolean active = true;
}
