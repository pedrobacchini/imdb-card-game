package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.MatchFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.ContinueOrStartMatchRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchCurrentStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.application.port.input.ContinueOrStartMatchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MatchFlowController implements MatchFlowApi {

    private final ContinueOrStartMatchUseCase continueOrStartMatchUseCase;

    public MatchFlowController(final ContinueOrStartMatchUseCase continueOrStartMatchUseCase) {
        this.continueOrStartMatchUseCase = continueOrStartMatchUseCase;
    }

    @Override
    public MatchCurrentStatusResponse startAndContinueMatch(ContinueOrStartMatchRequest continueOrStartMatchRequest) {
        final var game = continueOrStartMatchUseCase.execute(continueOrStartMatchRequest.toDomain());
        return MatchConverterHelper.toMatchCurrentStatusResponse(game);
    }

    @Override
    public void endMatch() {

    }

}
