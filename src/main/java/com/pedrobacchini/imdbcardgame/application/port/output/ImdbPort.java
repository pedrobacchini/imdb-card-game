package com.pedrobacchini.imdbcardgame.application.port.output;

import com.pedrobacchini.imdbcardgame.application.domain.Movie;

import java.util.List;

public interface ImdbPort {

    List<Movie> findMostPopularMovies(int page);

}
