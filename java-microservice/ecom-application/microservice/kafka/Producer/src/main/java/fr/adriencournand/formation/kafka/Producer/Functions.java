package fr.adriencournand.formation.kafka.Producer;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Functions {

    @Bean
    public Function<String,String> uppercase () {
        return value -> value.toUpperCase();
    }
}
