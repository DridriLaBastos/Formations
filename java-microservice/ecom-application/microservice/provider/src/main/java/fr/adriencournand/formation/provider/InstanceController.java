package fr.adriencournand.formation.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceController {

    @Value("${server.port}")
    private String port;

    private final String instanceId = java.util.UUID.randomUUID().toString();

    @GetMapping("/instance-info")
    public String GrtInstanceInfo() {
        System.out.println(("Request received at instance running on port" + port));
        return "Instance : @" + instanceId + ":" + port;
    }

}
