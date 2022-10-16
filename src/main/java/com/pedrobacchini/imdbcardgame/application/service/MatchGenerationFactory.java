package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.ImdbGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchGenerationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchGenerationFactory {

    private final ImdbCardGameProperty imdbCardGameProperty;

    public MatchGenerationStrategy acquireNewStrategy() {
        return switch (imdbCardGameProperty.getMatchStrategy()) {
            case ALPHABET -> new AlphabetGenerationStrategy();
            case IMDB -> new ImdbGenerationStrategy();
        };
    }

}
