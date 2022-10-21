package com.pedrobacchini.imdbcardgame.application.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void givenAValidParams_whenCallStarMatch_thenInstantiateAMatch() {
        final var expectedPlayerId = "1";
        final var expectedMatchId = "1";
        final var expectedMatchOptionsGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        final var expectedPoints = 0;
        final var expectedFails = 0;
        final var expectedStatus = Match.MatchStatus.PLAYING_GAME;

        final var actualMatch = Match.start(new MatchIdentification(expectedPlayerId, expectedMatchId), expectedMatchOptionsGenerationStrategy);

        assertNotNull(actualMatch);
        assertNotNull(actualMatch.getMatchIdentification());
        assertEquals(expectedPlayerId, actualMatch.getMatchIdentification().playerId());
        assertEquals(expectedMatchId, actualMatch.getMatchIdentification().matchId());
        assertEquals(expectedMatchOptionsGenerationStrategy, actualMatch.getMatchOptionsGenerationStrategy());
        assertEquals(expectedPoints, actualMatch.getPoints());
        assertEquals(expectedFails, actualMatch.getFails());
        assertNotNull(actualMatch.getCurrentMatchOptions());
        assertEquals(expectedStatus, actualMatch.getStatus());
    }

}