package com.pedrobacchini.imdbcardgame;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
@EnableConfigurationProperties(ImdbCardGameProperty.class)
public class ImdbCardGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbCardGameApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
		jackson2ObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		return jackson2ObjectMapperBuilder;
	}
}
