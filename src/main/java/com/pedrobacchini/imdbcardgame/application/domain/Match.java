package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Getter;

@Getter
public class Match {

    private final MatchIdentification matchIdentification;
    private int points = 0;
    private int fails = 0;
    private MatchOptions currentMatchOptions;
    private final MatchGenerationStrategy matchGenerationStrategy;

    private Match(final MatchIdentification matchIdentification, final MatchGenerationStrategy matchGenerationStrategy) {
        this.matchIdentification = matchIdentification;
        this.matchGenerationStrategy = matchGenerationStrategy;
        this.currentMatchOptions = matchGenerationStrategy.next();
    }

    public static Match start(final MatchIdentification matchIdentification, final MatchGenerationStrategy matchGenerationStrategy) {
        return new Match(matchIdentification, matchGenerationStrategy);
    }

    public Match nextPhase(final String playerChoice) {
        if (playerChoice.equals(currentMatchOptions.firstOption().option())) {
            applyOption(currentMatchOptions.firstOption());
        } else if (playerChoice.equals(currentMatchOptions.secondOption().option())) {
            applyOption(currentMatchOptions.secondOption());
        } else {
//            TODO add custom exception
            throw new IllegalArgumentException();
        }
        return this;
    }

    private void applyOption(final MatchOption matchOption) {
        if (matchOption.equals(currentMatchOptions.rightOption())) points++;
        else fails++;
        currentMatchOptions = matchGenerationStrategy.next();
    }

}
