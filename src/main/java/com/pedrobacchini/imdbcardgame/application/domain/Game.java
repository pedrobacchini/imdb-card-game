package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {

    private final String playerId;
    private final String gameId;

}
