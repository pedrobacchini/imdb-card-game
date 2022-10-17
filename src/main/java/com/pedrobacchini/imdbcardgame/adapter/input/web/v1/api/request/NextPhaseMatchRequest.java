package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request;

import com.pedrobacchini.imdbcardgame.application.command.NextPhaseMatchCommand;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NextPhaseMatchRequest {

    @NotBlank
    String playerId;
    @NotBlank
    String matchId;
    @NotBlank
    String playerChoice;

    public NextPhaseMatchCommand toCommand() {
        return new NextPhaseMatchCommand(new MatchIdentification(playerId, matchId), playerChoice);
    }
}
