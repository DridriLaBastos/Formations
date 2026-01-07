package fr.adriencournand.formation.consumer.httpinterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

// https://docs.spring.io/spring-framework/reference/6.2/integration/rest-clients.html#rest-http-interface

@Configuration
public class RestClientHttpInterfaceConfig {

    @Bean("restClientInterface")
    public IHttpInterfaceProvider RestClientHttpInterface() {
        RestClient client = RestClient.builder().baseUrl("http://localhost:8081").build();
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        IHttpInterfaceProvider service = proxyFactory.createClient(IHttpInterfaceProvider.class);

        return service;
    }
}
