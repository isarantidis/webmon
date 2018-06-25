package com.isarantidis.webmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

@SpringBootApplication
@EnableConfigurationProperties
public class WebmonApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebmonApplication.class, args);
	}
}
