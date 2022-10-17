package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Data;

@Data
public class Match {

    private final MatchIdentification matchIdentification;
    private final int points = 0;
    private final int fails = 0;
    private final MatchOptions currentMatchOptions;
    private final MatchGenerationStrategy matchGenerationStrategy;

    private Match(final MatchIdentification matchIdentification, final MatchGenerationStrategy matchGenerationStrategy) {
        this.matchIdentification = matchIdentification;
        this.matchGenerationStrategy = matchGenerationStrategy;
        this.currentMatchOptions = matchGenerationStrategy.next();
    }

    public static Match start(final MatchIdentification matchIdentification, final MatchGenerationStrategy matchGenerationStrategy) {
        return new Match(matchIdentification, matchGenerationStrategy);
    }

    public void nextPhase(final String playerChoice) {
//        MatchValidator.validatePlayerChoice(this, playerChoice);
    }

}
