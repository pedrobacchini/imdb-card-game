package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.domain.Game;
import com.pedrobacchini.imdbcardgame.application.dto.GameIdentification;

public interface ContinueOrStartGameUseCase {

    Game execute(GameIdentification gameIdentification);

}
