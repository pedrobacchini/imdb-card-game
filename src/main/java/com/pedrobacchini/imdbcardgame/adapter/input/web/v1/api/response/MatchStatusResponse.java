package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchStatusResponse {

    String playerId;
    String matchId;
    String status;
    int points;
    int fails;
    String firstOption;
    String secondOption;

}
