package fr.adriencournand.formation.consumer.httpinterface;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

// https://docs.spring.io/spring-framework/reference/6.2/integration/rest-clients.html#rest-http-interface
@Configuration
public class WebClientHttpInterfaceConfig {

    @Bean("httpInterfaceWebClientBuilder")
    @LoadBalanced
    WebClient.Builder HttpInterfaceWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean("webClientInterface")
    public IHttpInterfaceProvider WebClientHttpInterface(
            @Qualifier("httpInterfaceWebClientBuilder") WebClient.Builder builder) {
        WebClient client = builder.baseUrl("http://provider").build();
        WebClientAdapter adapter = WebClientAdapter.create(client);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        IHttpInterfaceProvider service = proxyFactory.createClient(IHttpInterfaceProvider.class);

        return service;
    }
}
