package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.ContinueOrStartMatchUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class ContinueOrStartMatchService implements ContinueOrStartMatchUseCase {

    private final MatchRepositoryPort matchRepositoryPort;
    private final MatchGenerationFactory matchGenerationFactory;

    public ContinueOrStartMatchService(final MatchRepositoryPort matchRepositoryPort, final MatchGenerationFactory matchGenerationFactory) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
        this.matchGenerationFactory = Objects.requireNonNull(matchGenerationFactory);
    }

    @Override
    public Match execute(final MatchIdentification matchIdentification) {
        return matchRepositoryPort.findGameByIdentification(matchIdentification)
            .orElseGet(() -> startNewMatch(matchIdentification));
    }

    private Match startNewMatch(final MatchIdentification matchIdentification) {
        final var newMatch = Match.start(matchIdentification, matchGenerationFactory.acquireNewStrategy());
        matchRepositoryPort.save(newMatch);
        return newMatch;
    }

}
