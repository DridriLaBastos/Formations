package fr.adriencournand.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;;

@RestController
public class ConfigController {
    @Value("${test.loaded:failure}")
    private String failure;

    @GetMapping("/failure")
    ResponseEntity<String> GetFailure() {
        return ResponseEntity.ok(failure);
    }
}
