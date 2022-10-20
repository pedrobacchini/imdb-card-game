package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Getter;

public enum MatchStrategy {
    ALPHABET(new AlphabetMatchOptionsGenerationStrategy()),
    IMDB(new ImdbMatchOptionsGenerationStrategy());

    @Getter
    private final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy;

    MatchStrategy(final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy) {
        this.matchOptionsGenerationStrategy = matchOptionsGenerationStrategy;
    }
}
