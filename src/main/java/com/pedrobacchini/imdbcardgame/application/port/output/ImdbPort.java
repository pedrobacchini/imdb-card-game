package com.pedrobacchini.imdbcardgame.application.port.output;

import com.pedrobacchini.imdbcardgame.application.domain.Movie;

import java.util.Set;

public interface ImdbPort {

    Set<Movie> findMostPopularMovies(int page);

}
