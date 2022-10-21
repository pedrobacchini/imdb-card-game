package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public final class MatchConverterHelper {

    public static MatchStatusResponse toMatchStatusResponse(Match match) {
        final var builder = MatchStatusResponse.builder()
            .playerId(match.getMatchIdentification().playerId())
            .matchId(match.getMatchIdentification().matchId())
            .status(match.getStatus().toString())
            .points(match.getPoints())
            .fails(match.getFails());
        if (match.getStatus().equals(Match.MatchStatus.PLAYING_GAME)) {
            builder.firstOption(match.getCurrentMatchOptions().firstOption().option())
                .secondOption(match.getCurrentMatchOptions().secondOption().option());
        }
        return builder.build();
    }

}
