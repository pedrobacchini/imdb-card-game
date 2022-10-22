package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.RankingApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.application.port.input.GetRankingUseCase;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class RankingController implements RankingApi {

    private final GetRankingUseCase getRankingUseCase;

    public RankingController(final GetRankingUseCase getRankingUseCase) {
        this.getRankingUseCase = Objects.requireNonNull(getRankingUseCase);
    }

    @Override
    public List<MatchStatusResponse> ranking() {
        return getRankingUseCase.execute()
            .stream().map(MatchConverterHelper::toMatchStatusResponse).collect(Collectors.toList());
    }

}
