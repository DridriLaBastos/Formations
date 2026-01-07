package fr.adriencournand.formation.consumer.webclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/webclient")
@RequiredArgsConstructor
public class WebClientController {

    private final WebClientProvider webClientProvider;

    @GetMapping("/instance")
    public Mono<String> GetInstance() {
        return webClientProvider.GetInstanceInfo();
    }

}
