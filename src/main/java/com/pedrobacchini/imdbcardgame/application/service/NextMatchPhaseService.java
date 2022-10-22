package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.command.PlayerMovementCommand;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.port.input.NextMatchPhaseUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import com.pedrobacchini.imdbcardgame.application.util.MatchServiceUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NextMatchPhaseService implements NextMatchPhaseUseCase {

    private final MatchRepositoryPort matchRepositoryPort;

    public NextMatchPhaseService(final MatchRepositoryPort matchRepositoryPort) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
    }

    @Override
    public Match execute(final PlayerMovementCommand playerMovementCommand) {
        return matchRepositoryPort.findByIdentificationAndStatus(playerMovementCommand.matchIdentification(), Match.MatchStatus.PLAYING_GAME)
            .map(match -> nextPhase(playerMovementCommand, match))
            .orElseThrow(MatchServiceUtils.notFound(playerMovementCommand.matchIdentification(), Match.MatchStatus.PLAYING_GAME));
    }

    private Match nextPhase(final PlayerMovementCommand playerMovementCommand, final Match match) {
        match.nextPhase(playerMovementCommand.playerMove());
        matchRepositoryPort.save(match);
        return match;
    }
}
