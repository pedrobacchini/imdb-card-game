package com.pedrobacchini.imdbcardgame.application.domain;

import com.pedrobacchini.imdbcardgame.application.exception.DomainException;

public class MatchValidator {

    private final Match match;

    public MatchValidator(final Match match) {
        this.match = match;
    }

    public void validate() {
        checkMatchIdentificationConstraints();
        checkMatchOptionsGenerationStrategyConstraints();
    }

    private void checkMatchIdentificationConstraints() {
        if (this.match.getMatchIdentification().playerId() == null) throw new DomainException("'playerId' should not be null");
        if (this.match.getMatchIdentification().matchId() == null) throw new DomainException("'matchId' should not be null");
    }

    private void checkMatchOptionsGenerationStrategyConstraints() {
        if (this.match.getMatchOptionsGenerationStrategy() == null) throw new DomainException("'strategy' should not be null");
    }

}
