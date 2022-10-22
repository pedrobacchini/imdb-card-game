package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.util.MatchTestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MatchConverterHelperTest {

    @Test
    void givenAOverMatch_whenCallsToMatchStatusResponse_shouldReturnMatchStatusResponseWithoutOptions() {
        final var expectedPlayerId = UUID.randomUUID();
        final var expectedMatchId = UUID.randomUUID();
        final var matchIdentification = new MatchIdentification(expectedPlayerId, expectedMatchId);
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        final var matchOver = MatchTestUtil.forceGameOverByFails(expectedMatch);

        final var actualMatchStatusResponse = MatchConverterHelper.toMatchStatusResponse(matchOver);

        assertEquals(expectedMatch.getMatchIdentification().playerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(expectedMatch.getMatchIdentification().matchId().toString(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatch.getStatus().toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedMatch.getPoints(), actualMatchStatusResponse.getPoints());
        assertEquals(expectedMatch.getFails(), actualMatchStatusResponse.getFails());
        assertNull(actualMatchStatusResponse.getFirstOption());
        assertNull(actualMatchStatusResponse.getSecondOption());
    }

}