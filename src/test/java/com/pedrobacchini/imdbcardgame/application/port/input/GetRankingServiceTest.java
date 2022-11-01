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
        final var expectedMatchSize = 10;
        final var expectedMatchs = MatchTestUtil.generateListMatchOver(expectedMatchSize);
        when(matchRepositoryPort.findByStatusOrderedLimitedTo(eq(Match.MatchStatus.GAME_OVER), eq(GetRankingService.LIMIT_RANKING)))
            .thenReturn(expectedMatchs);

        final var actualMatchs = getRankingService.execute();

        verify(matchRepositoryPort, times(1))
            .findByStatusOrderedLimitedTo(eq(Match.MatchStatus.GAME_OVER), eq(GetRankingService.LIMIT_RANKING));
        assertEquals(expectedMatchSize, actualMatchs.size());
        assertEquals(expectedMatchs, actualMatchs);
    }
}