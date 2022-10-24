package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Getter;

public enum MatchStrategy {
    ALPHABET(AlphabetMatchOptionsGenerationStrategy.class),
    IMDB(ImdbMatchOptionsGenerationStrategy.class);

    @Getter
    private final Class matchOptionsGenerationStrategy;

    MatchStrategy(final Class matchOptionsGenerationStrategy) {
        this.matchOptionsGenerationStrategy = matchOptionsGenerationStrategy;
    }
}
