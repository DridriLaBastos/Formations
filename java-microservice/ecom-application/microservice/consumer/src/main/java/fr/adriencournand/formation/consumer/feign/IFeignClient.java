package fr.adriencournand.formation.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "provider")
public interface IFeignClient {

    @GetMapping("/instance-info")
    String GetInstanceInfo();
}
