package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import com.pedrobacchini.imdbcardgame.application.port.output.ImdbPort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class InicializeMoviesService {

    public static final int NUMBER_OF_PAGES_MOVIES = 10;
    private final ImdbPort imdbPort;

    public InicializeMoviesService(final ImdbPort imdbPort) {
        this.imdbPort = Objects.requireNonNull(imdbPort);
    }

    public Set<Movie> inicializeMovies() {
        Set<Movie> movies = new HashSet<>();
        for (int page = 1; page <= NUMBER_OF_PAGES_MOVIES; page++) {
            final var mostPopularMovies = imdbPort.findMostPopularMovies(page);
            movies.addAll(mostPopularMovies);
        }
        return movies;
    }

}
