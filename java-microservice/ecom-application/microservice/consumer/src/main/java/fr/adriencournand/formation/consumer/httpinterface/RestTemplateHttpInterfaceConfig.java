package fr.adriencournand.formation.consumer.httpinterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

// https://docs.spring.io/spring-framework/reference/6.2/integration/rest-clients.html#rest-http-interface
@Configuration
public class RestTemplateHttpInterfaceConfig {

    @Bean("restTemplateInterface")
    public IHttpInterfaceProvider RestTemplateHttpInterface() {
        RestTemplate template = new RestTemplate();
        template.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(template);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        IHttpInterfaceProvider service = proxyFactory.createClient(IHttpInterfaceProvider.class);

        return service;
    }
}
