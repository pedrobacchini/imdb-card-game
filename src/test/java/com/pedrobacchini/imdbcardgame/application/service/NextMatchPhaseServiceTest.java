package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.command.PlayerMovementCommand;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.exception.NotFoundException;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NextMatchPhaseServiceTest {

    @InjectMocks
    private NextMatchPhaseService nextMatchPhaseService;

    @Mock
    private MatchRepositoryPort matchRepositoryPort;

    @Test
    void givenAValidPlayerMovementCommand_whenCallsNextMatchPhase_shouldReturnMatchWithNewStatus() {
        final var expectedMatchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var expectedMatch = Match.start(expectedMatchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        final var matchOption = expectedMatch.getCurrentMatchOptions().rightOption();
        final var playerMovementCommand = new PlayerMovementCommand(expectedMatchIdentification, matchOption.value());

        when(matchRepositoryPort.findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME)))
            .thenReturn(Optional.of(expectedMatch));

        nextMatchPhaseService.execute(playerMovementCommand);

        verify(matchRepositoryPort, times(1))
            .findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME));
        verify(matchRepositoryPort, times(1)).save(eq(expectedMatch));
    }

    @Test
    void givenAValidPlayerMovementCommand_whenCallsNextMatchPhase_shouldNotFoundMatch() {
        final var expectedMatchIdentification = new MatchIdentification(UUID.randomUUID(),  UUID.randomUUID());
        final var playerMovementCommand = new PlayerMovementCommand(expectedMatchIdentification, "option1");

        when(matchRepositoryPort.findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME)))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> nextMatchPhaseService.execute(playerMovementCommand));

        verify(matchRepositoryPort, times(1))
            .findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME));
        verify(matchRepositoryPort, never()).save(any());
    }
}