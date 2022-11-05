package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchStrategy;
import com.pedrobacchini.imdbcardgame.application.service.InicializeMoviesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchGenerationFactoryTest {


    @Test
    void givenAPropertyAlphabet_whenCallsAcquireNewStrategy_shouldReturnNewAlphabetMatchOptionsGenerationStrategy() {
        final ImdbCardGameProperty imdbCardGameProperty = mock(ImdbCardGameProperty.class);
        final InicializeMoviesService inicializeMoviesService = mock(InicializeMoviesService.class);
        when(imdbCardGameProperty.getMatchStrategy()).thenReturn(MatchStrategy.ALPHABET);
        final MatchGenerationFactory matchGenerationFactory = new MatchGenerationFactory(imdbCardGameProperty, inicializeMoviesService);
        final var expectedStrategy = instanceOf(AlphabetMatchOptionsGenerationStrategy.class);

        final var actualStrategy = matchGenerationFactory.acquireNewStrategy();

        verify(imdbCardGameProperty, times(1)).getMatchStrategy();
        verify(inicializeMoviesService, never()).inicializeMovies();

        assertNotNull(actualStrategy);
        assertThat(actualStrategy, expectedStrategy);
    }

    @Test
    void givenAPropertyIMDB_whenCallsAcquireNewStrategy_shouldReturnNewImdbMatchOptionsGenerationStrategy() {
        final ImdbCardGameProperty imdbCardGameProperty = mock(ImdbCardGameProperty.class);
        final InicializeMoviesService inicializeMoviesService = mock(InicializeMoviesService.class);
        when(imdbCardGameProperty.getMatchStrategy()).thenReturn(MatchStrategy.IMDB);
        final MatchGenerationFactory matchGenerationFactory = new MatchGenerationFactory(imdbCardGameProperty, inicializeMoviesService);
        final var expectedStrategy = instanceOf(MatchOptionsGenerationStrategy.class);

        final var actualStrategy = matchGenerationFactory.acquireNewStrategy();

        verify(imdbCardGameProperty, times(1)).getMatchStrategy();
        verify(inicializeMoviesService, times(1)).inicializeMovies();

        assertNotNull(actualStrategy);
        assertThat(actualStrategy, expectedStrategy);
    }

}