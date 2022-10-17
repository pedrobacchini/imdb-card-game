package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.ImdbGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchGenerationStrategy;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MatchGenerationFactory {

    private final ImdbCardGameProperty imdbCardGameProperty;

    public MatchGenerationFactory(final ImdbCardGameProperty imdbCardGameProperty) {
        this.imdbCardGameProperty = Objects.requireNonNull(imdbCardGameProperty);
    }

    public MatchGenerationStrategy acquireNewStrategy() {
        return switch (imdbCardGameProperty.getMatchStrategy()) {
            case ALPHABET -> new AlphabetGenerationStrategy();
            case IMDB -> new ImdbGenerationStrategy();
        };
    }

}
