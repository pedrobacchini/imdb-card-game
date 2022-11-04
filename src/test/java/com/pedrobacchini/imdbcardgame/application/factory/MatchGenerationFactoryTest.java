package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchStrategy;
import com.pedrobacchini.imdbcardgame.application.port.output.MovieRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchGenerationFactoryTest {

    @InjectMocks
    private MatchGenerationFactory matchGenerationFactory;

    @Mock
    private ImdbCardGameProperty imdbCardGameProperty;

    @Mock
    private MovieRepositoryPort movieRepositoryPort;

    @Test
    void givenAPropertyAlphabet_whenCallsAcquireNewStrategy_shouldReturnNewAlphabetMatchOptionsGenerationStrategy() {
        final var expectedStrategy = instanceOf(AlphabetMatchOptionsGenerationStrategy.class);
        when(imdbCardGameProperty.getMatchStrategy()).thenReturn(MatchStrategy.ALPHABET);

        final var actualStrategy = matchGenerationFactory.acquireNewStrategy();

        verify(imdbCardGameProperty, times(1)).getMatchStrategy();
        verify(movieRepositoryPort, never()).getAll();

        assertNotNull(actualStrategy);
        assertThat(actualStrategy, expectedStrategy);
    }

    @Test
    void givenAPropertyIMDB_whenCallsAcquireNewStrategy_shouldReturnNewImdbMatchOptionsGenerationStrategy() {
        final var expectedStrategy = instanceOf(MatchOptionsGenerationStrategy.class);
        when(imdbCardGameProperty.getMatchStrategy()).thenReturn(MatchStrategy.IMDB);

        final var actualStrategy = matchGenerationFactory.acquireNewStrategy();

        verify(imdbCardGameProperty, times(1)).getMatchStrategy();
        verify(movieRepositoryPort, times(1)).getAll();

        assertNotNull(actualStrategy);
        assertThat(actualStrategy, expectedStrategy);
    }

}