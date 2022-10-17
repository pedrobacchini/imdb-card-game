package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class ContinueOrStartMatchRequest {

    @NotBlank
    String playerId;
    @NotBlank
    String matchId;

    public MatchIdentification toDomain() {
        return new MatchIdentification(playerId, matchId);
    }

}
