package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.MatchFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.ContinueOrStartMatchRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.NextPhaseMatchRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.application.port.input.ContinueOrStartMatchUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.NextPhaseMatchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MatchFlowController implements MatchFlowApi {

    private final ContinueOrStartMatchUseCase continueOrStartMatchUseCase;
    private final NextPhaseMatchUseCase nextPhaseMatchUseCase;

    @Override
    public MatchStatusResponse continueOrStartMatch(final ContinueOrStartMatchRequest continueOrStartMatchRequest) {
        final var match = continueOrStartMatchUseCase.execute(continueOrStartMatchRequest.toDomain());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public MatchStatusResponse nextPhaseMatch(final NextPhaseMatchRequest nextPhaseMatchRequest) {
        final var match = nextPhaseMatchUseCase.execute(nextPhaseMatchRequest.toCommand());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public void endMatch() {

    }

}
