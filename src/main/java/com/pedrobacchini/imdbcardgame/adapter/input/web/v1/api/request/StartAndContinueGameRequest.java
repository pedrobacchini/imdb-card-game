package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.application.domain.Game;
import lombok.Data;

@Data
public class StartAndContinueGameRequest {

    private final String playerId;
    private final String gameId;

    public Game toDomain() {
        return Game.builder().playerId(playerId).gameId(gameId).build();
    }
}
