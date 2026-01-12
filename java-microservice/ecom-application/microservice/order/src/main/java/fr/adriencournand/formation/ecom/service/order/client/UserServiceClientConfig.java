package fr.adriencournand.formation.ecom.service.order.client;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserServiceClientConfig {
    @Bean
    IUserServiceClient UserRestClientInterface(RestClient.Builder builder) {
        RestClient client = builder.baseUrl("http://ecom-user-microservice")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, ((request, response) -> Optional.empty()))
                .build();
        RestClientAdapter adaptor = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adaptor).build();

        return factory.createClient(IUserServiceClient.class);
    }
}
