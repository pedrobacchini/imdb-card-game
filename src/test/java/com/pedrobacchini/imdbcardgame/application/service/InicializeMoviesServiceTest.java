package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Movie;
import com.pedrobacchini.imdbcardgame.application.port.output.ImdbPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InicializeMoviesServiceTest {

    @InjectMocks
    private InicializeMoviesService inicializeMoviesService;

    @Mock
    private ImdbPort imdbPort;

    @Test
    void whenCallsInicializeMovies_shouldReturnSetOfMovies() {

        when(imdbPort.findMostPopularMovies(anyInt()))
            .thenReturn(Set.of(new Movie("A", 1F)))
            .thenReturn(Set.of(new Movie("B", 1F)))
            .thenReturn(Set.of(new Movie("C", 1F)));

        final var movies = inicializeMoviesService.inicializeMovies();

        verify(imdbPort, times(10)).findMostPopularMovies(anyInt());
        assertEquals(3, movies.size());
    }

}