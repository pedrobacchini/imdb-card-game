package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.controller;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.MatchFlowApi;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.adapter.security.Player;
import com.pedrobacchini.imdbcardgame.application.command.PlayerMovementCommand;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.MatchOverUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.NextMatchPhaseUseCase;
import com.pedrobacchini.imdbcardgame.application.port.input.StartOrContinueMatchUseCase;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

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
    public MatchStatusResponse startOrContinueMatch(final MatchIdentificationRequest matchIdentificationRequest, final Player player) {
        final var matchIdentification = new MatchIdentification(player.getPlayerId(), UUID.fromString(matchIdentificationRequest.getMatchId()));
        final var match = startOrContinueMatchUseCase.execute(matchIdentification);
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public MatchStatusResponse nextPhase(final PlayerMovimentRequest playerMovimentRequest, final Player player) {
        final var matchIdentification = new MatchIdentification(player.getPlayerId(), UUID.fromString(playerMovimentRequest.getMatchId()));
        final var match = nextMatchPhaseUseCase.execute(new PlayerMovementCommand(matchIdentification, playerMovimentRequest.getPlayerMove()));
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

    @Override
    public MatchStatusResponse matchOver(MatchIdentificationRequest matchIdentificationRequest, final Player player) {
        final var matchIdentification = new MatchIdentification(player.getPlayerId(), UUID.fromString(matchIdentificationRequest.getMatchId()));
        final var match = matchOverUseCase.execute(matchIdentification);
        return MatchConverterHelper.toMatchStatusResponse(match);
    }

}
