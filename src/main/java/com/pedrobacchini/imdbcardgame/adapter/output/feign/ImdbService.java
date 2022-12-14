package com.pedrobacchini.imdbcardgame.adapter.output.feign;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import com.pedrobacchini.imdbcardgame.application.port.output.ImdbPort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImdbService implements ImdbPort {

    private final ImdbClient imdbClient;
    private final ImdbCardGameProperty imdbCardGameProperty;

    public ImdbService(final ImdbClient imdbClient, final ImdbCardGameProperty imdbCardGameProperty) {
        this.imdbClient = Objects.requireNonNull(imdbClient);
        this.imdbCardGameProperty = Objects.requireNonNull(imdbCardGameProperty);
    }

    @Override
    public Set<Movie> findMostPopularMovies(final int page) {
        final var imdbApi = imdbCardGameProperty.getImdbApi();
        return imdbClient.findMostPopularMovies(imdbApi.getKey(), imdbApi.getLanguage(), page)
            .getResults()
            .stream()
            .map(imdbMovie -> new Movie(imdbMovie.getTitle(), imdbMovie.getVoteAverage()))
            .collect(Collectors.toSet());
    }

}
