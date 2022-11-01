package com.pedrobacchini.imdbcardgame.application.config;

import com.pedrobacchini.imdbcardgame.application.port.output.ImdbPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class InicializeConfig {

    private final ImdbPort imdbPort;

    public InicializeConfig(final ImdbPort imdbPort) {
        this.imdbPort = Objects.requireNonNull(imdbPort);
    }

    @Bean
    public void inicializeMovies() {
        final var mostPopularMovies = imdbPort.findMostPopularMovies(1);
        System.out.println(mostPopularMovies);
    }

}
