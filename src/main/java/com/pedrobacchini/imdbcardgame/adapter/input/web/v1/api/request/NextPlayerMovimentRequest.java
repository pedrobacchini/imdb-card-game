package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.application.command.NextPlayerMovimentCommand;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NextPlayerMovimentRequest {

    @NotBlank
    String playerId;
    @NotBlank
    String matchId;
    @NotBlank
    String playerMove;

    public NextPlayerMovimentCommand toCommand() {
        return new NextPlayerMovimentCommand(new MatchIdentification(playerId, matchId), playerMove);
    }
}
