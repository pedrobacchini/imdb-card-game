package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.command.PlayerMovementCommand;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public interface NextMatchPhaseUseCase {

    Match execute(PlayerMovementCommand playerMovementCommand);

}
