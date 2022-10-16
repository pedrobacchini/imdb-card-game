package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.GameFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.ContinueOrStartGameRequest;
import com.pedrobacchini.imdbcardgame.application.port.input.ContinueOrStartGameUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GameFlowController implements GameFlowApi {

    private final ContinueOrStartGameUseCase continueOrStartGameUseCase;

    public GameFlowController(final ContinueOrStartGameUseCase continueOrStartGameUseCase) {
        this.continueOrStartGameUseCase = continueOrStartGameUseCase;
    }

    @Override
    public void startAndContinueGame(ContinueOrStartGameRequest continueOrStartGameRequest) {
        continueOrStartGameUseCase.execute(continueOrStartGameRequest.toDTO());
    }

    @Override
    public void endGame() {

    }

}
