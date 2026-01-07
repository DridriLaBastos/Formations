package fr.adriencournand.formation.consumer.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@EnableFeignClients
@RequestMapping("/api/feign")
@RequiredArgsConstructor
public class FeignController {

    private final IFeignClient feignClient;

    @GetMapping("/instance")
    public String GetInstance() {
        return feignClient.GetInstanceInfo();
    }

}
