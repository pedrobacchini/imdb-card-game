package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Getter;

@Getter
public class Match {

    private final MatchIdentification matchIdentification;
    private int points = 0;
    private int fails = 0;
    private MatchOptions currentMatchOptions;
    private final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy;

    private Match(final MatchIdentification matchIdentification, final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy) {
        this.matchIdentification = matchIdentification;
        this.matchOptionsGenerationStrategy = matchOptionsGenerationStrategy;
        this.currentMatchOptions = matchOptionsGenerationStrategy.generateInitialMatchOptions();
    }

    public static Match start(final MatchIdentification matchIdentification, final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy) {
        return new Match(matchIdentification, matchOptionsGenerationStrategy);
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
        currentMatchOptions = matchOptionsGenerationStrategy.generateNextMatchOptions()
            //            TODO add custom exception
            .orElseThrow(IllegalStateException::new);
    }

}
