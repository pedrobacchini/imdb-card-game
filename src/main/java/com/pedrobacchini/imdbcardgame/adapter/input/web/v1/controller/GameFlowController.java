package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.GameFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.StartAndContinueGameRequest;
import com.pedrobacchini.imdbcardgame.application.port.input.StartAndContinueGameUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GameFlowController implements GameFlowApi {

    private final StartAndContinueGameUseCase startAndContinueGameUseCase;

    public GameFlowController(final StartAndContinueGameUseCase startAndContinueGameUseCase) {
        this.startAndContinueGameUseCase = startAndContinueGameUseCase;
    }

    @Override
    public void startAndContinueGame(StartAndContinueGameRequest startAndContinueGameRequest) {
        startAndContinueGameUseCase.execute(startAndContinueGameRequest.toDomain());
    }

    @Override
    public void endGame() {

    }

}
