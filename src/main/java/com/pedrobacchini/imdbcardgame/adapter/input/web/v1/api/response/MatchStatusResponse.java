package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchStatusResponse {

    String playerId;
    String matchId;
    int points;
    int fails;
    String firstOption;
    String secondOption;

}
