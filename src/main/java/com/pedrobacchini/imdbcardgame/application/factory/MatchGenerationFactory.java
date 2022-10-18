package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.ImdbMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MatchGenerationFactory {

    private final ImdbCardGameProperty imdbCardGameProperty;

    public MatchGenerationFactory(final ImdbCardGameProperty imdbCardGameProperty) {
        this.imdbCardGameProperty = Objects.requireNonNull(imdbCardGameProperty);
    }

    public MatchOptionsGenerationStrategy acquireNewStrategy() {
        return switch (imdbCardGameProperty.getMatchStrategy()) {
            case ALPHABET -> new AlphabetMatchOptionsGenerationStrategy();
            case IMDB -> new ImdbMatchOptionsGenerationStrategy();
        };
    }

}
