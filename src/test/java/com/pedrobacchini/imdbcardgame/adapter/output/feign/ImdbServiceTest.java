package com.pedrobacchini.imdbcardgame.adapter.output.feign;

import com.pedrobacchini.imdbcardgame.adapter.output.feign.response.ImdbMovie;
import com.pedrobacchini.imdbcardgame.adapter.output.feign.response.ImdbResponse;
import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImdbServiceTest {

    @InjectMocks
    private ImdbService imdbService;

    @Mock
    private ImdbClient imdbClient;

    @Mock
    private ImdbCardGameProperty imdbCardGameProperty;

    @Test
    void givenAValidPage_whenCallsfindMostPopularMovies_shouldReturnSetOfMovies() {
        final var page = 1;
        final var apiKey = "123";
        final var language = "pt-BR";
        final var imdbApi = new ImdbCardGameProperty.ImdbApi();
        imdbApi.setKey(apiKey);
        imdbApi.setLanguage(language);
        when(imdbCardGameProperty.getImdbApi()).thenReturn(imdbApi);
        final var expectedMostPopularMovies = List.of(
            new ImdbMovie("ABCD", 1F), new ImdbMovie("DCBA", 9F));
        final var imdbResponse = new ImdbResponse(expectedMostPopularMovies);
        when(imdbClient.findMostPopularMovies(eq(apiKey), eq(language), eq(page))).thenReturn(imdbResponse);

        final var actualMostPopularMovies = imdbService.findMostPopularMovies(page);

        verify(imdbCardGameProperty, times(1)).getImdbApi();
        verify(imdbClient, times(1)).findMostPopularMovies(eq(apiKey), eq(language), eq(page));

        assertEquals(expectedMostPopularMovies.size(), actualMostPopularMovies.size());
        assertEquals(2, actualMostPopularMovies.size());
        final var movieStream = expectedMostPopularMovies
            .stream()
            .map(imdbMovie -> new Movie(imdbMovie.getTitle(), imdbMovie.getVoteAverage()))
            .collect(Collectors.toSet());
        assertThat(actualMostPopularMovies).hasSameElementsAs(movieStream);
    }

}