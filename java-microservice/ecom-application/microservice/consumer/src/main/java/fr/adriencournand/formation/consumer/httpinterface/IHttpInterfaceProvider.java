package fr.adriencournand.formation.consumer.httpinterface;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface IHttpInterfaceProvider {

    @GetExchange("/instance-info")
    String GetInstanceInfo();

}
