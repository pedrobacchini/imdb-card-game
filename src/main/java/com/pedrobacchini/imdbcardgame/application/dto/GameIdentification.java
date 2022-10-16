package com.pedrobacchini.imdbcardgame.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameIdentification {

    String playerId;
    String gameId;

}
