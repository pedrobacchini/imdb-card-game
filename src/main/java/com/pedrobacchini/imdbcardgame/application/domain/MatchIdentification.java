package com.pedrobacchini.imdbcardgame.application.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchIdentification {

    String playerId;
    String gameId;

}
