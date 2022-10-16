package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOption;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.ContinueOrStartMatchUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContinueOrStartMatchService implements ContinueOrStartMatchUseCase {

    private final MatchRepositoryPort matchRepositoryPort;

    public ContinueOrStartMatchService(final MatchRepositoryPort matchRepositoryPort) {
        this.matchRepositoryPort = matchRepositoryPort;
    }

    @Override
    public Match execute(final MatchIdentification matchIdentification) {
        return matchRepositoryPort.findGameByIdentification(matchIdentification)
            .orElse(startNewGame(matchIdentification));
    }

    private Match startNewGame(final MatchIdentification matchIdentification) {
        final var newGame = new Match(matchIdentification.getPlayerId(), new MatchOption("1", "2"));
        matchRepositoryPort.save(newGame);
        return newGame;
    }

}
