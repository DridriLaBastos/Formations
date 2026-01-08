package fr.adriencournand.formation.ecom.service.order.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import fr.adriencournand.formation.ecom.service.order.dto.ProductResponse;

@HttpExchange
public interface IProductServiceClient {

    @GetExchange("/api/products/{id}")
    ProductResponse GetProductDetails(@PathVariable Long id);
}
