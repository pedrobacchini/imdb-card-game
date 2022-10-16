package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.application.dto.GameIdentification;
import lombok.Value;

@Value
public class ContinueOrStartGameRequest {

    String playerId;
    String gameId;

    public GameIdentification toDTO() {
        return GameIdentification.builder().playerId(playerId).gameId(gameId).build();
    }

}
