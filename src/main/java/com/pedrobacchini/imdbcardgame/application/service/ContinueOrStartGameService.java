package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Game;
import com.pedrobacchini.imdbcardgame.application.dto.GameIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.ContinueOrStartGameUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.GameRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContinueOrStartGameService implements ContinueOrStartGameUseCase {

    private final GameRepositoryPort gameRepositoryPort;

    public ContinueOrStartGameService(final GameRepositoryPort gameRepositoryPort) {
        this.gameRepositoryPort = gameRepositoryPort;
    }

    @Override
    public Game execute(final GameIdentification gameIdentification) {
        return gameRepositoryPort.findGameByIdentification(gameIdentification)
            .orElse(new Game(gameIdentification.getPlayerId()));
    }

}
