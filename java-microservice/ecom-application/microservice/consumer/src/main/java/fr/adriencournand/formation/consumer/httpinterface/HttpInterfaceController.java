package fr.adriencournand.formation.consumer.httpinterface;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/http-interface")
public class HttpInterfaceController {

    @Qualifier("webClientInterface")
    private final IHttpInterfaceProvider webClientInterface;

    @Qualifier("restClientInterface")
    private final IHttpInterfaceProvider restClientInterface;

    @Qualifier("restTemplateInterface")
    private final IHttpInterfaceProvider restTemplateInterface;

    @GetMapping("/web-client-instance")
    public String GetWebClientInstanceInfo() {
        return webClientInterface.GetInstanceInfo();
    }

    @GetMapping("/rest-client-instance")
    public String GetRestClientInstanceInfo() {
        return restClientInterface.GetInstanceInfo();
    }

    @GetMapping("/rest-template-instance")
    public String GetRestTemplateInstanceInfo() {
        return restTemplateInterface.GetInstanceInfo();
    }
}
