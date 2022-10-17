package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.command.NextPhaseMatchCommand;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.exception.NotFoundException;
import com.pedrobacchini.imdbcardgame.application.port.input.NextPhaseMatchUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Supplier;

@Service
public class NextPhaseMatchService implements NextPhaseMatchUseCase {

    private final MatchRepositoryPort matchRepositoryPort;

    public NextPhaseMatchService(final MatchRepositoryPort matchRepositoryPort) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
    }

    @Override
    public Match execute(final NextPhaseMatchCommand nextPhaseMatchCommand) {
        return matchRepositoryPort.findGameByIdentification(nextPhaseMatchCommand.matchIdentification())
            .map(match -> nextPhase(match, nextPhaseMatchCommand.playerChoice()))
            .orElseThrow(notFound(nextPhaseMatchCommand));
    }

    private Supplier<NotFoundException> notFound(final NextPhaseMatchCommand nextPhaseMatchCommand) {
        return () -> new NotFoundException("Match with Identification %s was not found".formatted(nextPhaseMatchCommand.matchIdentification()));
    }

    private Match nextPhase(final Match match, final String playerChoice) {
        //        if(!playerChoice.equals(match.getCurrentMatchOptions().firstOption().option()) && )
        match.nextPhase(playerChoice);
        return match;
    }

}
