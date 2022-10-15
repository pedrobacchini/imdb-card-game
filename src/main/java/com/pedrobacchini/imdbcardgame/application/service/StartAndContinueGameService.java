package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Game;
import com.pedrobacchini.imdbcardgame.application.port.input.StartAndContinueGameUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StartAndContinueGameService implements StartAndContinueGameUseCase {

    @Override
    public void execute(final Game game) {
        log.info("start and continue game service started");
    }

}
