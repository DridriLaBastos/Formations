package fr.adriencournand.configclient;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;;

@RestController
@RefreshScope
public class ConfigController {
    @Value("${crypted-config.aes:error}")
    private String aesConfig;

    @Value("${crypted-config.rsa:error}")
    private String rsaConfig;

    @GetMapping("/crypted-config")
    ResponseEntity<List<String>> GetFailure() {
        return ResponseEntity.ok(List.of("AES: '" + aesConfig + "'", "RSA: '" + rsaConfig + "'"));
    }
}
