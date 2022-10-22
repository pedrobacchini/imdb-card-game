package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.MatchFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.application.port.input.NextMatchPhaseUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.MatchOverUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.StartOrContinueMatchUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
public class MatchFlowController implements MatchFlowApi {

    private final StartOrContinueMatchUseCase startOrContinueMatchUseCase;
    private final NextMatchPhaseUseCase nextMatchPhaseUseCase;
    private final MatchOverUseCase matchOverUseCase;

    public MatchFlowController(
        final StartOrContinueMatchUseCase startOrContinueMatchUseCase,
        final NextMatchPhaseUseCase nextMatchPhaseUseCase,
        final MatchOverUseCase matchOverUseCase) {
        this.startOrContinueMatchUseCase = Objects.requireNonNull(startOrContinueMatchUseCase);
        this.nextMatchPhaseUseCase = Objects.requireNonNull(nextMatchPhaseUseCase);
        this.matchOverUseCase = Objects.requireNonNull(matchOverUseCase);
    }

    @Override
    public MatchStatusResponse startOrContinueMatch(final MatchIdentificationRequest matchIdentificationRequest) {
        final var match = startOrContinueMatchUseCase.execute(matchIdentificationRequest.toDomain());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public MatchStatusResponse nextPhase(final PlayerMovimentRequest playerMovimentRequest) {
        final var match = nextMatchPhaseUseCase.execute(playerMovimentRequest.toCommand());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public MatchStatusResponse matchOver(MatchIdentificationRequest matchIdentificationRequest) {
        final var match = matchOverUseCase.execute(matchIdentificationRequest.toDomain());
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

}
