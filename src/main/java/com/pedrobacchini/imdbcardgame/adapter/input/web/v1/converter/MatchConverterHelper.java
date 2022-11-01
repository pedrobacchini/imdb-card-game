package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public final class MatchConverterHelper {

    private MatchConverterHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static MatchStatusResponse toMatchStatusResponse(Match match) {
        final var builder = MatchStatusResponse.builder()
            .playerId(match.getMatchIdentification().playerId().toString())
            .matchId(match.getMatchIdentification().matchId().toString())
            .status(match.getStatus().toString())
            .points(match.getPoints())
            .fails(match.getFails());
        if (match.getStatus().equals(Match.MatchStatus.PLAYING_GAME)) {
            builder.firstOption(match.getCurrentMatchOptions().firstOption().value())
                .secondOption(match.getCurrentMatchOptions().secondOption().value());
        }
        return builder.build();
    }

}
