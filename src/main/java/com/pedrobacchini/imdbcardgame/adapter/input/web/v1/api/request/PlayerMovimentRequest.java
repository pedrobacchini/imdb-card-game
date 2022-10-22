package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constraint.Uuid;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class PlayerMovimentRequest {

    @NotBlank
    @Uuid
    String matchId;
    @NotBlank
    String playerMove;
}
