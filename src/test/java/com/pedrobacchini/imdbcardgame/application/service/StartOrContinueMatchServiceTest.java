package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.factory.MatchGenerationFactory;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartOrContinueMatchServiceTest {

    @InjectMocks
    private StartOrContinueMatchService startOrContinueMatchService;

    @Mock
    private MatchRepositoryPort matchRepositoryPort;

    @Mock
    private MatchGenerationFactory matchGenerationFactory;

    @Test
    void givenAValidIdentification_whenCallsStartOrContinueMatch_shouldReturnNewStartMatch() {
        final var expectedPlayerId = UUID.randomUUID();
        final var expectedMatchId = UUID.randomUUID();
        final var expectedMatchIdentification = new MatchIdentification(expectedPlayerId, expectedMatchId);
        final var expectedMatchOptionsGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();

        when(matchRepositoryPort.findByIdentification(eq(expectedMatchIdentification)))
            .thenReturn(Optional.empty());
        when(matchGenerationFactory.acquireNewStrategy())
            .thenReturn(expectedMatchOptionsGenerationStrategy);

        startOrContinueMatchService.execute(expectedMatchIdentification);

        verify(matchRepositoryPort, times(1)).findByIdentification(eq(expectedMatchIdentification));
        verify(matchGenerationFactory, times(1)).acquireNewStrategy();
        verify(matchRepositoryPort, times(1)).save(any());
    }

    @Test
    void givenAValidIdentification_whenCallsStartOrContinueMatch_shouldReturnFoundMatch() {
        final var expectedPlayerId = UUID.randomUUID();
        final var expectedMatchId = UUID.randomUUID();
        final var expectedMatchIdentification = new MatchIdentification(expectedPlayerId, expectedMatchId);
        final var expectedMatchOptionsGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        final var expectedMatch = Match.start(expectedMatchIdentification, expectedMatchOptionsGenerationStrategy);

        when(matchRepositoryPort.findByIdentification(eq(expectedMatchIdentification)))
            .thenReturn(Optional.of(expectedMatch));

        final var actualMatch = startOrContinueMatchService.execute(expectedMatchIdentification);

        verify(matchRepositoryPort, times(1)).findByIdentification(eq(expectedMatchIdentification));
        verify(matchGenerationFactory, never()).acquireNewStrategy();
        verify(matchRepositoryPort, never()).save(any());
        assertEquals(expectedMatch, actualMatch);
    }

}