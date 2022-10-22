package com.pedrobacchini.imdbcardgame.adapter.output.memory;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.util.MatchTestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchRepositoryTest {

    @Test
    void givenAValidMatch_whenCallsSaveAndfindByIdentification_shouldReturnSameMatch() {
        final var matchRepository = new MatchRepository();
        final var matchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());

        matchRepository.save(expectedMatch);

        final var actualMatchOptional = matchRepository.findByIdentification(expectedMatch.getMatchIdentification());

        assertTrue(actualMatchOptional.isPresent());
        final var actualMatch = actualMatchOptional.get();
        assertEquals(expectedMatch, actualMatch);
    }

    @Test
    void givenAValidMatch_whenCallsSaveAndfindByIdentificationAndStatus_shouldReturnSameMatch() {
        final var matchRepository = new MatchRepository();
        final var matchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());

        matchRepository.save(expectedMatch);

        final var actualMatchOptional = matchRepository.findByIdentificationAndStatus(expectedMatch.getMatchIdentification(), expectedMatch.getStatus());
        assertTrue(actualMatchOptional.isPresent());
        final var actualMatch = actualMatchOptional.get();
        assertEquals(expectedMatch, actualMatch);
    }

    @Test
    void givenAValidMatchOver_whenCallsfindByIdentificationAndStatus_shouldReturnEmpty() {
        final var matchRepository = new MatchRepository();
        final var matchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        MatchTestUtil.forceGameOverByFails(expectedMatch);
        matchRepository.save(expectedMatch);

        final var actualMatchOptional = matchRepository.findByIdentificationAndStatus(matchIdentification, Match.MatchStatus.PLAYING_GAME);
        assertFalse(actualMatchOptional.isPresent());
    }

}