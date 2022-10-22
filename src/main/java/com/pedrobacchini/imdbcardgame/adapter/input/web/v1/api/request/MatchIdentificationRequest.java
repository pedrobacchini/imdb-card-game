package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constraint.Uuid;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class MatchIdentificationRequest {

    @NotBlank
    @Uuid
    String matchId;

}
