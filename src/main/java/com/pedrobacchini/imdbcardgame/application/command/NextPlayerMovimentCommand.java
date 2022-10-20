package com.pedrobacchini.imdbcardgame.application.command;

import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;

public record NextPlayerMovimentCommand(MatchIdentification matchIdentification, String playerMove) {

}
