package fr.adriencournand.example.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
    // Configure a secret resource that will only be accessible via an authorized
    // request
    @GetMapping("/data")
    public String data() {
        return "Hello from service 2";
    }
}
