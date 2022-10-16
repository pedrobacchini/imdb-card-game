package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.MatchFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.ContinueOrStartMatchRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
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
    public MatchStatusResponse continueOrStartMatch(ContinueOrStartMatchRequest continueOrStartMatchRequest) {
        final var match = continueOrStartMatchUseCase.execute(continueOrStartMatchRequest.toDomain());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public void endMatch() {

    }

}
