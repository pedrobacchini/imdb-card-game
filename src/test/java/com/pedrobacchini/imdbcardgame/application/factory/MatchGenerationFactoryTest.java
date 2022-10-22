package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.ImdbMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchGenerationFactoryTest {

    @InjectMocks
    private MatchGenerationFactory matchGenerationFactory;

    @Mock
    private ImdbCardGameProperty imdbCardGameProperty;

    @Test
    void givenAPropertyAlphabet_whenCallsAcquireNewStrategy_shouldReturnNewAlphabetMatchOptionsGenerationStrategy() {
        final var expectedStrategy = instanceOf(AlphabetMatchOptionsGenerationStrategy.class);
        when(imdbCardGameProperty.getMatchStrategy()).thenReturn(MatchStrategy.ALPHABET);

        final var actualStrategy = matchGenerationFactory.acquireNewStrategy();

        assertNotNull(actualStrategy);
        assertThat(actualStrategy, expectedStrategy);
    }

    @Test
    void givenAPropertyIMDB_whenCallsAcquireNewStrategy_shouldReturnNewImdbMatchOptionsGenerationStrategy() {
        final var expectedStrategy = instanceOf(ImdbMatchOptionsGenerationStrategy.class);
        when(imdbCardGameProperty.getMatchStrategy()).thenReturn(MatchStrategy.IMDB);

        final var actualStrategy = matchGenerationFactory.acquireNewStrategy();

        assertNotNull(actualStrategy);
        assertThat(actualStrategy, expectedStrategy);
    }

}