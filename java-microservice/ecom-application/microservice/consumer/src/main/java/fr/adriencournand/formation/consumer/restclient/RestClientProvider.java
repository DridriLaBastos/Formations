package fr.adriencournand.formation.consumer.restclient;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestClientProvider {
    private final RestClient restClient;

    public String GetInstanceInfo() {
        return restClient.get().uri("/instance-info").retrieve().body(String.class);
    }
}
