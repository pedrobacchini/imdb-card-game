package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Match {

    private final MatchIdentification matchIdentification;
    private final int points = 0;
    private final int fails = 0;
    private final Set<MatchOption> optionsAlreadyChosen = new HashSet<>();
    private final MatchOption currentMatchOption;

    public Match(final String playerId, MatchOption matchOption) {
        this.matchIdentification = new MatchIdentification(playerId, UUID.randomUUID().toString());
        this.currentMatchOption = matchOption;
    }

}
