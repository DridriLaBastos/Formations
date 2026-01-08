package fr.adriencournand.formation.consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestTemplateClient {

    private static final String PROVIDER_URL = "http://provider";

    private final RestTemplate restTemplate;

    public String GetInstanceInfo() {
        return restTemplate.getForObject(PROVIDER_URL + "/instance-info", String.class);
    }
}
