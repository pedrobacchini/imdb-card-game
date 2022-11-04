package com.pedrobacchini.imdbcardgame.application.port.output;

import com.pedrobacchini.imdbcardgame.application.domain.Movie;

import java.util.Set;

public interface MovieRepositoryPort {

    void saveAll(Set<Movie> mostPopularMovies);

    Set<Movie> getAll();

}
