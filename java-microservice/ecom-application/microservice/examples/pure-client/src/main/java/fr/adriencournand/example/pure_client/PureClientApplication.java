package fr.adriencournand.example.pure_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PureClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(PureClientApplication.class, args);
	}

	// Exposing an rest template bean to be use as http request to make API calls
	// or whatever the fuck that means
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Storing in memory authorized client and token
	 */
	@Bean
	public OAuth2AuthorizedClientService auth2AuthorizedClientService(
			ClientRegistrationRepository clientRegistrationRepository) {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
	}

	// REMINDER: Je me suis arrêté à 24:34 dans la vidéo où il commence à expliquer
	// toute cette merde que je viens d'écrire
	@Bean
	public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(ClientRegistrationRepository repos,
			OAuth2AuthorizedClientService clientService) {

		// Helper class to manage token requests
		var manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(repos, clientService);

		// Telling Spring Security to use client credential flow
		OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials()
				.build();
		manager.setAuthorizedClientProvider(provider);
		return manager;
	}

	/**
	 * Automatically executed when the application starts
	 */
	@Bean
	public CommandLineRunner run(OAuth2AuthorizedClientManager manager,
			RestTemplate rest,
			@Value("${service2.url}") String service2Url) {
		return args -> {
			var authRequest = OAuth2AuthorizeRequest
					// Get client ID from the configuration : keycloak-client.client-id
					.withClientRegistrationId("keycloak-client")
					// Just a random name
					.principal("machine")
					.build();

			var client = manager.authorize(authRequest);
			String token = client.getAccessToken().getTokenValue();

			HttpHeaders header = new HttpHeaders();
			header.setBearerAuth(token);

			var resp = rest.exchange("http://localhost:8082" + "/data", HttpMethod.GET, new HttpEntity<>(header),
					String.class);

			System.out.println("Response from service 2 : " + resp);
		};
	}

}
