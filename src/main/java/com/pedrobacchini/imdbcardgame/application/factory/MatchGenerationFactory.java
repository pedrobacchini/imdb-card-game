package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import com.pedrobacchini.imdbcardgame.application.service.InicializeMoviesService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class MatchGenerationFactory {

    private final ImdbCardGameProperty imdbCardGameProperty;
    private final MatchStrategy matchStrategy;
    private Set<Movie> movies;

    public MatchGenerationFactory(
        final ImdbCardGameProperty imdbCardGameProperty,
        final InicializeMoviesService inicializeMoviesService) {
        this.imdbCardGameProperty = Objects.requireNonNull(imdbCardGameProperty);
        this.matchStrategy = imdbCardGameProperty.getMatchStrategy();
        if (matchStrategy.equals(MatchStrategy.IMDB)) {
            this.movies = inicializeMoviesService.inicializeMovies();
        }
    }

    public MatchOptionsGenerationStrategy acquireNewStrategy() {
        switch (matchStrategy) {
            case ALPHABET -> {
                return new AlphabetMatchOptionsGenerationStrategy();
            }
            case IMDB -> {
                return new MatchOptionsGenerationStrategy(movies);
            }
            default -> throw new IllegalStateException("Unexpected value: " + imdbCardGameProperty.getMatchStrategy());
        }
    }

}
