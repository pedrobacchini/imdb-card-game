package com.pedrobacchini.imdbcardgame.application.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchOverServiceTest {

    @InjectMocks
    private MatchOverService matchOverService;

    @Mock
    private MatchRepositoryPort matchRepositoryPort;

    @Test
    void givenAValidMatchIdentification_whenCallsMatchOver_shouldReturnMatchOver() {
        final var expectedMatchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var expectedMatchStatus = Match.MatchStatus.GAME_OVER;
        final var expectedMatch = Match.start(expectedMatchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        when(matchRepositoryPort.findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME)))
            .thenReturn(Optional.of(expectedMatch));

        final var actualMatch = matchOverService.execute(expectedMatchIdentification);

        verify(matchRepositoryPort, times(1))
            .findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME));
        verify(matchRepositoryPort, times(1)).save(eq(expectedMatch));
        assertEquals(expectedMatchIdentification, actualMatch.getMatchIdentification());
        assertEquals(expectedMatchStatus, actualMatch.getStatus());
        assertNull(actualMatch.getCurrentMatchOptions());
    }

    @Test
    void givenANotFoundMatchIdentification_whenCallsMatchOver_shouldNotFoundMatch() {
        final var expectedMatchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        when(matchRepositoryPort.findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME)))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> matchOverService.execute(expectedMatchIdentification));

        verify(matchRepositoryPort, times(1))
            .findByIdentificationAndStatus(eq(expectedMatchIdentification), eq(Match.MatchStatus.PLAYING_GAME));
        verify(matchRepositoryPort, never()).save(any());
    }

}