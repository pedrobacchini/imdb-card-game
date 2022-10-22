package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import com.pedrobacchini.imdbcardgame.util.MatchTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRankingServiceTest {

    @InjectMocks
    private GetRankingService getRankingService;

    @Mock
    private MatchRepositoryPort matchRepositoryPort;

    @Test
    void whenCallsGetRankingService_shouldReturnRankingOfMatchs() {
        final var matchs = MatchTestUtil.generateListMatchOver(5);
        when(matchRepositoryPort.findAllByStatus(eq(Match.MatchStatus.GAME_OVER)))
            .thenReturn(matchs);

        final var actualMatchs = getRankingService.execute();

        verify(matchRepositoryPort, times(1)).findAllByStatus(eq(Match.MatchStatus.GAME_OVER));
        assertEquals(5, actualMatchs.size());
        final var expectedMatchs = matchs.stream().sorted().toList();
        assertEquals(expectedMatchs, actualMatchs);
    }


    @Test
    void whenCallsGetRankingService_shouldReturnRankingOfMatchsLimited() {
        final var matchs = MatchTestUtil.generateListMatchOver(15);
        when(matchRepositoryPort.findAllByStatus(eq(Match.MatchStatus.GAME_OVER)))
            .thenReturn(matchs);

        final var actualMatchs = getRankingService.execute();

        verify(matchRepositoryPort, times(1)).findAllByStatus(eq(Match.MatchStatus.GAME_OVER));
        assertEquals(10, actualMatchs.size());
        final var expectedMatchs = matchs.stream().sorted().limit(10).toList();
        assertEquals(expectedMatchs, actualMatchs);
    }

}