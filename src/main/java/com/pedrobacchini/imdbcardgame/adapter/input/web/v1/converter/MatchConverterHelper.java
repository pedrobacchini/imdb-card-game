package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public class MatchConverterHelper {

    public static MatchStatusResponse toMatchStatusResponse(Match match) {
        return MatchStatusResponse.builder()
            .playerId(match.getMatchIdentification().getPlayerId())
            .matchId(match.getMatchIdentification().getMatchId())
            .points(match.getPoints())
            .fails(match.getFails())
            .firstOption(match.getCurrentMatchOption().getFirstOption())
            .secondOption(match.getCurrentMatchOption().getSecondOption())
            .build();
    }
}
