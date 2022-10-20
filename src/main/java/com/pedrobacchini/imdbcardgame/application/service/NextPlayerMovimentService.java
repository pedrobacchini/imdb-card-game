package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.command.NextPlayerMovimentCommand;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.exception.NotFoundException;
import com.pedrobacchini.imdbcardgame.application.port.input.NextPlayerMovimentUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Supplier;

@Service
public class NextPlayerMovimentService implements NextPlayerMovimentUseCase {

    private final MatchRepositoryPort matchRepositoryPort;

    public NextPlayerMovimentService(final MatchRepositoryPort matchRepositoryPort) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
    }

    @Override
    public Match execute(final NextPlayerMovimentCommand nextPlayerMovimentCommand) {
        return matchRepositoryPort.findByIdentificationAndStatus(nextPlayerMovimentCommand.matchIdentification(), Match.MatchStatus.PLAYING_GAME)
            .map(match -> match.nextPlayerMovement(nextPlayerMovimentCommand.playerMove()))
            .orElseThrow(notFound(nextPlayerMovimentCommand));
    }

    private Supplier<NotFoundException> notFound(final NextPlayerMovimentCommand nextPlayerMovimentCommand) {
        return () -> new NotFoundException("Match with Identification %s was not found".formatted(nextPlayerMovimentCommand.matchIdentification()));
    }

}
