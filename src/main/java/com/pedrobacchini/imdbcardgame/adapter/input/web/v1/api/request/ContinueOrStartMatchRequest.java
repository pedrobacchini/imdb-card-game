package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import lombok.Value;

@Value
public class ContinueOrStartMatchRequest {

    String playerId;
    String matchId;

    public MatchIdentification toDomain() {
        return MatchIdentification.builder().playerId(playerId).matchId(matchId).build();
    }

}
