package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.command.NextPlayerMovimentCommand;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public interface NextPlayerMovimentUseCase {

    Match execute(NextPlayerMovimentCommand nextPlayerMovimentCommand);

}
