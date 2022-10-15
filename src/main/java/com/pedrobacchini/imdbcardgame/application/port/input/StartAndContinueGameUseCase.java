package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.domain.Game;

public interface StartAndContinueGameUseCase {

    void execute(Game game);

}
