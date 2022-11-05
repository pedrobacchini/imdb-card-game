package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import com.pedrobacchini.imdbcardgame.application.service.InicializeMoviesService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MatchGenerationFactory {

    private final MatchStrategy matchStrategy;
    private Set<Movie> movies;

    public MatchGenerationFactory(
        final ImdbCardGameProperty imdbCardGameProperty,
        final InicializeMoviesService inicializeMoviesService) {
        this.matchStrategy = imdbCardGameProperty.getMatchStrategy();
        if (matchStrategy.equals(MatchStrategy.IMDB)) {
            this.movies = inicializeMoviesService.inicializeMovies();
        }
    }

    public MatchOptionsGenerationStrategy acquireNewStrategy() {
        return switch (matchStrategy) {
            case ALPHABET -> new AlphabetMatchOptionsGenerationStrategy();
            case IMDB -> new MatchOptionsGenerationStrategy(movies);
        };
    }

}
