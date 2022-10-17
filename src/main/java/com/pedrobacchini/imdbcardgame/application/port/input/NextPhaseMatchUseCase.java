package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.command.NextPhaseMatchCommand;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public interface NextPhaseMatchUseCase {

    Match execute(NextPhaseMatchCommand nextPhaseMatchCommand);

}
