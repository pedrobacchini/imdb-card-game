package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.MatchFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.StartOrContinueMatchRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.application.port.input.StartOrContinueMatchUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.NextMatchPhaseUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
public class MatchFlowController implements MatchFlowApi {

    private final StartOrContinueMatchUseCase startOrContinueMatchUseCase;
    private final NextMatchPhaseUseCase nextMatchPhaseUseCase;

    public MatchFlowController(final StartOrContinueMatchUseCase startOrContinueMatchUseCase, final NextMatchPhaseUseCase nextMatchPhaseUseCase) {
        this.startOrContinueMatchUseCase = Objects.requireNonNull(startOrContinueMatchUseCase);
        this.nextMatchPhaseUseCase = Objects.requireNonNull(nextMatchPhaseUseCase);
    }

    @Override
    public MatchStatusResponse startOrContinueMatch(final StartOrContinueMatchRequest startOrContinueMatchRequest) {
        final var match = startOrContinueMatchUseCase.execute(startOrContinueMatchRequest.toDomain());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public MatchStatusResponse nextPhase(final PlayerMovimentRequest playerMovimentRequest) {
        final var match = nextMatchPhaseUseCase.execute(playerMovimentRequest.toCommand());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public void endMatch() {

    }

}
