package com.pedrobacchini.imdbcardgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(ImdbCardGameProperty.class)
public class ImdbCardGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImdbCardGameApplication.class, args);
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
    }

}
