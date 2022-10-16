package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public class MatchConverterHelper {

    public static MatchStatusResponse toMatchStatusResponse(Match match) {
        return MatchStatusResponse.builder()
            .playerId(match.getMatchIdentification().playerId())
            .matchId(match.getMatchIdentification().matchId())
            .points(match.getPoints())
            .fails(match.getFails())
            .firstOption(match.getCurrentMatchOptions().firstOption().option())
            .secondOption(match.getCurrentMatchOptions().secondOption().option())
            .build();
    }
}
