package com.pedrobacchini.imdbcardgame.adapter.output.memory;

import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import com.pedrobacchini.imdbcardgame.application.port.output.MovieRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class MovieRepository implements MovieRepositoryPort {

    private final Map<String, Movie> movies = new HashMap<>();

    @Override
    public void saveAll(final Set<Movie> mostPopularMovies) {
        movies.putAll(mostPopularMovies.stream().collect(Collectors.toMap(Movie::name, Function.identity())));
    }

    @Override
    public Set<Movie> getAll() {
        return new HashSet<>(movies.values());
    }

}
