package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.NextMatchPhaseUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.StartOrContinueMatchUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchFlowControllerTest {


    @InjectMocks
    private MatchFlowController matchFlowController;

    @Mock
    private StartOrContinueMatchUseCase startOrContinueMatchUseCase;

    @Mock
    private NextMatchPhaseUseCase nextMatchPhaseUseCase;

    @Test
    void givenAValidStartOrContinueMatchRequest_whenCallsStartOrContinueMatch_shouldReturnNewMatchOrContinueMatch() {
        final var expectedPlayerId = UUID.randomUUID();
        final var expectedMatchId = UUID.randomUUID();
        final var matchIdentification = new MatchIdentification(expectedPlayerId, expectedMatchId);
        final var matchIdentificationRequest = new MatchIdentificationRequest(expectedPlayerId.toString(), expectedMatchId.toString());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());

        when(startOrContinueMatchUseCase.execute(any())).thenReturn(expectedMatch);

        final var actualMatchStatusResponse = matchFlowController.startOrContinueMatch(matchIdentificationRequest);

        verify(startOrContinueMatchUseCase, times(1)).execute(argThat((arg) -> {
            assertEquals(expectedPlayerId, arg.playerId());
            assertEquals(expectedMatchId, arg.matchId());
            return true;
        }));
        verify(nextMatchPhaseUseCase, never()).execute(any());
        assertEquals(expectedMatch.getMatchIdentification().playerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(expectedMatch.getMatchIdentification().matchId().toString(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatch.getStatus().toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedMatch.getPoints(), actualMatchStatusResponse.getPoints());
        assertEquals(expectedMatch.getFails(), actualMatchStatusResponse.getFails());
        assertEquals(expectedMatch.getCurrentMatchOptions().firstOption().option(), actualMatchStatusResponse.getFirstOption());
        assertEquals(expectedMatch.getCurrentMatchOptions().secondOption().option(), actualMatchStatusResponse.getSecondOption());
    }

    @Test
    void givenAValidPlayerMovementCommand_whenCallsNextMatchPhase_shouldReturnMatchWithNewStatus() {
        final var expectedPlayerId = UUID.randomUUID();
        final var expectedMatchId = UUID.randomUUID();
        final var matchIdentification = new MatchIdentification(expectedPlayerId, expectedMatchId);
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        final var expectedPlayerMove = expectedMatch.getCurrentMatchOptions().rightOption().option();
        final var playerMovimentRequest = new PlayerMovimentRequest(
            matchIdentification.playerId().toString(),
            matchIdentification.matchId().toString(),
            expectedPlayerMove);

        when(nextMatchPhaseUseCase.execute(any())).thenReturn(expectedMatch);

        final var actualMatchStatusResponse = matchFlowController.nextPhase(playerMovimentRequest);

        verify(startOrContinueMatchUseCase, never()).execute(any());
        verify(nextMatchPhaseUseCase, times(1)).execute(argThat(arg -> {
            assertEquals(expectedPlayerId, arg.matchIdentification().playerId());
            assertEquals(expectedMatchId, arg.matchIdentification().matchId());
            assertEquals(expectedPlayerMove, arg.playerMove());
            return true;
        }));
        assertEquals(expectedMatch.getMatchIdentification().playerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(expectedMatch.getMatchIdentification().matchId().toString(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatch.getStatus().toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedMatch.getPoints(), actualMatchStatusResponse.getPoints());
        assertEquals(expectedMatch.getFails(), actualMatchStatusResponse.getFails());
        assertEquals(expectedMatch.getCurrentMatchOptions().firstOption().option(), actualMatchStatusResponse.getFirstOption());
        assertEquals(expectedMatch.getCurrentMatchOptions().secondOption().option(), actualMatchStatusResponse.getSecondOption());
    }

}