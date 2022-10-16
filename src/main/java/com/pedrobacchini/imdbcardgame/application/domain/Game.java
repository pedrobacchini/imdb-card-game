package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Game {

    private final String playerId;
    private final String gameId;

    public Game(final String playerId) {
        this.playerId = playerId;
        this.gameId = UUID.randomUUID().toString();
    }

}
