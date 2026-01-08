package fr.adriencournand.formation.consumer.httpinterface;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

// https://docs.spring.io/spring-framework/reference/6.2/integration/rest-clients.html#rest-http-interface

@Configuration
public class RestClientHttpInterfaceConfig {

    @Bean("httpInterfaceLoadBalancedRestClientBuilder")
    @LoadBalanced
    public RestClient.Builder HttpInterfaceLoadBalancedRestClient() {
        return RestClient.builder();
    }

    @Bean("restClientInterface")
    public IHttpInterfaceProvider RestClientHttpInterface(
            @Qualifier("httpInterfaceLoadBalancedRestClientBuilder") RestClient.Builder builder) {
        RestClient client = builder.baseUrl("http://provider").build();
        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        IHttpInterfaceProvider service = proxyFactory.createClient(IHttpInterfaceProvider.class);

        return service;
    }
}
