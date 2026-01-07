package fr.adriencournand.formation.consumer.restclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rest-client")
public class RestClientController {

    private final RestClientProvider restClientProvider;

    @GetMapping("/instance")
    public String GetInstance() {
        return restClientProvider.GetInstanceInfo();
    }

}
