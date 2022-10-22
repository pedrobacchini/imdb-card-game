package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.factory.MatchGenerationFactory;
import com.pedrobacchini.imdbcardgame.application.port.input.StartOrContinueMatchUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class StartOrContinueMatchService implements StartOrContinueMatchUseCase {

    private final MatchRepositoryPort matchRepositoryPort;
    private final MatchGenerationFactory matchGenerationFactory;

    public StartOrContinueMatchService(final MatchRepositoryPort matchRepositoryPort, final MatchGenerationFactory matchGenerationFactory) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
        this.matchGenerationFactory = Objects.requireNonNull(matchGenerationFactory);
    }

    @Override
    public Match execute(final MatchIdentification matchIdentification) {
        return matchRepositoryPort.findByIdentification(matchIdentification)
            .orElseGet(() -> startNewMatch(matchIdentification));
    }

    private Match startNewMatch(final MatchIdentification matchIdentification) {
        final var newMatch = Match.start(matchIdentification, matchGenerationFactory.acquireNewStrategy());
        newMatch.validate();
        matchRepositoryPort.save(newMatch);
        return newMatch;
    }

}
