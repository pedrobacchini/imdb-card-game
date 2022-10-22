package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.application.port.input.GetRankingUseCase;
import com.pedrobacchini.imdbcardgame.util.MatchTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RankingControllerTest {

    @InjectMocks
    private RankingController rankingController;

    @Mock
    private GetRankingUseCase getRankingUseCase;

    @Test
    void whenCallsRanking_shouldReturnRankingOfMatchs() {
        when(getRankingUseCase.execute()).thenReturn(MatchTestUtil.generateListMatchOver(3));

        final var ranking = rankingController.ranking();

        verify(getRankingUseCase, times(1)).execute();
        assertEquals(3, ranking.size());
    }

}