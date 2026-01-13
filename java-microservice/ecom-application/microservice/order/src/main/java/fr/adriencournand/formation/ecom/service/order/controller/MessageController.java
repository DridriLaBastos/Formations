package fr.adriencournand.formation.ecom.service.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@Configuration
public class MessageController {

    @Value("${app.message:Hello World}")
    private String message;

    @GetMapping("/message")
    @RateLimiter(name = "orderServiceLimiter", fallbackMethod = "MessageControllerFallback")
    public String GetMessage() {
        return message;
    }

    public String MessageControllerFallback(Exception e) {
        return "Message Controller Fallback";
    }
}
