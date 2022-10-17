package com.pedrobacchini.imdbcardgame.application.command;

import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;

public record NextPhaseMatchCommand(MatchIdentification matchIdentification, String playerChoice) {

}
