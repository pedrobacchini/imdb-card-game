package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchCurrentStatusResponse;
import com.pedrobacchini.imdbcardgame.application.domain.Match;

public class MatchConverterHelper {

    public static MatchCurrentStatusResponse toMatchCurrentStatusResponse(Match match) {
        return MatchCurrentStatusResponse.builder()
            .firstOption(match.getCurrentMatchOption().getFirstOption())
            .secondOption(match.getCurrentMatchOption().getSecondOption())
            .build();
    }
}
