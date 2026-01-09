package fr.adriencournand.formation.ecom.service.order.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import fr.adriencournand.formation.ecom.service.order.dto.UserResponse;

@HttpExchange
public interface IUserServiceClient {

    @GetExchange("/api/users/{id}")
    UserResponse GetUserInfo(@PathVariable String id);

}
