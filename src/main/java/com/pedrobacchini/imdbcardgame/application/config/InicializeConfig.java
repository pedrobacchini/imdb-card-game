package com.pedrobacchini.imdbcardgame.application.config;

import com.pedrobacchini.imdbcardgame.application.port.output.ImdbPort;
import com.pedrobacchini.imdbcardgame.application.port.output.MovieRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

//@Configuration
public class InicializeConfig {

    public static final int NUMBER_OF_PAGES_MOVIES = 10;
    private final ImdbPort imdbPort;

    private final MovieRepositoryPort movieRepositoryPort;

    public InicializeConfig(final ImdbPort imdbPort, final MovieRepositoryPort movieRepositoryPort) {
        this.imdbPort = Objects.requireNonNull(imdbPort);
        this.movieRepositoryPort = Objects.requireNonNull(movieRepositoryPort);
    }

    @Bean
    public void inicializeMovies() {
        for (int page = 1; page <= NUMBER_OF_PAGES_MOVIES; page++) {
            final var mostPopularMovies = imdbPort.findMostPopularMovies(page);
            movieRepositoryPort.saveAll(mostPopularMovies);
        }
    }

}
