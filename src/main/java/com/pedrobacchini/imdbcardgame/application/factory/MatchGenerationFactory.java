package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.port.output.MovieRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MatchGenerationFactory {

    private final ImdbCardGameProperty imdbCardGameProperty;
    private final MovieRepositoryPort movieRepositoryPort;

    public MatchGenerationFactory(final ImdbCardGameProperty imdbCardGameProperty, final MovieRepositoryPort movieRepositoryPort) {
        this.imdbCardGameProperty = Objects.requireNonNull(imdbCardGameProperty);
        this.movieRepositoryPort = Objects.requireNonNull(movieRepositoryPort);
    }

    public MatchOptionsGenerationStrategy acquireNewStrategy() {
        switch (imdbCardGameProperty.getMatchStrategy()) {
            case ALPHABET -> {
                return new AlphabetMatchOptionsGenerationStrategy();
            }
            case IMDB -> {
                final var movies = movieRepositoryPort.getAll();
                return new MatchOptionsGenerationStrategy(movies);
            }
            default -> throw new IllegalStateException("Unexpected value: " + imdbCardGameProperty.getMatchStrategy());
        }
    }

}
