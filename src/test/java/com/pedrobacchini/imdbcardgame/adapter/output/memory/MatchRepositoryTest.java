package com.pedrobacchini.imdbcardgame.adapter.output.memory;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.util.MatchTestUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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

        final var actualMatchOptional = matchRepository
            .findByIdentificationAndStatus(expectedMatch.getMatchIdentification(), expectedMatch.getStatus());
        assertTrue(actualMatchOptional.isPresent());
        final var actualMatch = actualMatchOptional.get();
        assertEquals(expectedMatch, actualMatch);
    }

    @Test
    void givenAValidMatchOver_whenCallsFindByIdentificationAndStatus_shouldReturnEmpty() {
        final var matchRepository = new MatchRepository();
        final var matchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var expectedMatch = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        MatchTestUtil.forceGameOverByFails(expectedMatch);
        matchRepository.save(expectedMatch);

        final var actualMatchOptional = matchRepository.findByIdentificationAndStatus(matchIdentification, Match.MatchStatus.PLAYING_GAME);
        assertFalse(actualMatchOptional.isPresent());
    }

    @Test
    void whenCallsfindByStatusOrderedLimitedToWithGAME_OVER_shouldReturnCorrectly() {
        final var matchRepository = new MatchRepository();
        final var expectedMatches = MatchTestUtil.generateListMatchOver(5);
        expectedMatches.forEach(matchRepository::save);

        final var actualMatchs = matchRepository.findByStatusOrderedLimitedTo(Match.MatchStatus.GAME_OVER, 5);
        assertThat(expectedMatches).hasSameElementsAs(actualMatchs);
    }

    @Test
    void whenCallsfindByStatusOrderedLimitedToWithLimit2_shouldReturnCorrectly() {
        final var matchRepository = new MatchRepository();
        final var expectedMatches = MatchTestUtil.generateListMatchOver(5);
        expectedMatches.forEach(matchRepository::save);

        final var actualMatchs = matchRepository.findByStatusOrderedLimitedTo(Match.MatchStatus.GAME_OVER, 2);
        assertEquals(2, actualMatchs.size());
    }

}