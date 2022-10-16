package com.pedrobacchini.imdbcardgame.application.port.output;

import com.pedrobacchini.imdbcardgame.application.domain.Game;
import com.pedrobacchini.imdbcardgame.application.dto.GameIdentification;

import java.util.Optional;

public interface GameRepositoryPort {

    Optional<Game> findGameByIdentification(GameIdentification gameIdentification);

}
