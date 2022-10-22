package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constraint.Uuid;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Value
public class MatchIdentificationRequest {

    @NotBlank
    @Uuid
    String playerId;
    @NotBlank
    @Uuid
    String matchId;

    public MatchIdentification toDomain() {
        return new MatchIdentification(UUID.fromString(playerId), UUID.fromString(matchId));
    }

}
