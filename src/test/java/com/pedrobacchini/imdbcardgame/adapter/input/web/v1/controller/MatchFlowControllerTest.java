package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.adapter.security.Player;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.MatchOverUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.NextMatchPhaseUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.StartOrContinueMatchUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
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

    @Mock
    private MatchOverUseCase matchOverUseCase;

    @Test
    void givenAValidStartOrContinueMatchRequest_whenCallsStartOrContinueMatch_shouldReturnNewMatchOrContinueMatch() {
        final var expectedPlayer = new Player(new User("player", "password", new HashSet<>()));
        final var expectedMatchId = UUID.randomUUID();
        final var matchIdentification = new MatchIdentification(expectedPlayer.getPlayerId(), UUID.randomUUID());
        final var matchIdentificationRequest = new MatchIdentificationRequest(expectedMatchId.toString());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());

        when(startOrContinueMatchUseCase.execute(any())).thenReturn(expectedMatch);

        final var actualMatchStatusResponse = matchFlowController.startOrContinueMatch(matchIdentificationRequest, expectedPlayer);

        verify(startOrContinueMatchUseCase, times(1)).execute(argThat((arg) -> {
            assertEquals(expectedPlayer.getPlayerId(), arg.playerId());
            assertEquals(expectedMatchId, arg.matchId());
            return true;
        }));
        verify(nextMatchPhaseUseCase, never()).execute(any());
        verify(matchOverUseCase, never()).execute(any());
        assertEquals(expectedMatch.getMatchIdentification().playerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(expectedMatch.getMatchIdentification().matchId().toString(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatch.getStatus().toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedMatch.getPoints(), actualMatchStatusResponse.getPoints());
        assertEquals(expectedMatch.getFails(), actualMatchStatusResponse.getFails());
        assertEquals(expectedMatch.getCurrentMatchOptions().firstOption().value(), actualMatchStatusResponse.getFirstOption());
        assertEquals(expectedMatch.getCurrentMatchOptions().secondOption().value(), actualMatchStatusResponse.getSecondOption());
    }

    @Test
    void givenAValidPlayerMovementCommand_whenCallsNextMatchPhase_shouldReturnMatchWithNewStatus() {
        final var expectedPlayer = new Player(new User("player", "password", new HashSet<>()));
        final var expectedMatchId = UUID.randomUUID();
        final var matchIdentification = new MatchIdentification(expectedPlayer.getPlayerId(), expectedMatchId);
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        final var expectedPlayerMove = expectedMatch.getCurrentMatchOptions().rightOption().value();
        final var playerMovimentRequest = new PlayerMovimentRequest(matchIdentification.matchId().toString(), expectedPlayerMove);

        when(nextMatchPhaseUseCase.execute(any())).thenReturn(expectedMatch);

        final var actualMatchStatusResponse = matchFlowController.nextPhase(playerMovimentRequest, expectedPlayer);

        verify(startOrContinueMatchUseCase, never()).execute(any());
        verify(nextMatchPhaseUseCase, times(1)).execute(argThat(arg -> {
            assertEquals(expectedPlayer.getPlayerId(), arg.matchIdentification().playerId());
            assertEquals(expectedMatchId, arg.matchIdentification().matchId());
            assertEquals(expectedPlayerMove, arg.playerMove());
            return true;
        }));
        verify(matchOverUseCase, never()).execute(any());
        assertEquals(expectedMatch.getMatchIdentification().playerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(expectedMatch.getMatchIdentification().matchId().toString(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatch.getStatus().toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedMatch.getPoints(), actualMatchStatusResponse.getPoints());
        assertEquals(expectedMatch.getFails(), actualMatchStatusResponse.getFails());
        assertEquals(expectedMatch.getCurrentMatchOptions().firstOption().value(), actualMatchStatusResponse.getFirstOption());
        assertEquals(expectedMatch.getCurrentMatchOptions().secondOption().value(), actualMatchStatusResponse.getSecondOption());
    }

    @Test
    void givenAValidMatchIdentification_whenCallsNextMatchPhase_shouldReturnMatchWithNewStatus() {
        final var expectedPlayer = new Player(new User("player", "password", new HashSet<>()));
        final var expectedMatchId = UUID.randomUUID();
        final var matchIdentification = new MatchIdentification(expectedPlayer.getPlayerId(), expectedMatchId);
        final var matchIdentificationRequest = new MatchIdentificationRequest(expectedMatchId.toString());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());

        when(matchOverUseCase.execute(eq(matchIdentification))).thenReturn(expectedMatch);

        final var actualMatchStatusResponse = matchFlowController.matchOver(matchIdentificationRequest, expectedPlayer);

        verify(startOrContinueMatchUseCase, never()).execute(any());
        verify(nextMatchPhaseUseCase, never()).execute(any());
        verify(matchOverUseCase, times(1)).execute(argThat(arg -> {
            assertEquals(expectedPlayer.getPlayerId(), arg.playerId());
            assertEquals(expectedMatchId, arg.matchId());
            return true;
        }));
        assertEquals(expectedMatch.getMatchIdentification().playerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(expectedMatch.getMatchIdentification().matchId().toString(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatch.getStatus().toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedMatch.getPoints(), actualMatchStatusResponse.getPoints());
        assertEquals(expectedMatch.getFails(), actualMatchStatusResponse.getFails());
        assertEquals(expectedMatch.getCurrentMatchOptions().firstOption().value(), actualMatchStatusResponse.getFirstOption());
        assertEquals(expectedMatch.getCurrentMatchOptions().secondOption().value(), actualMatchStatusResponse.getSecondOption());
    }

}