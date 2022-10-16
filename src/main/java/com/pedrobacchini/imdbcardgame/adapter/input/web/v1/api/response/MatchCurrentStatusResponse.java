package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MatchCurrentStatusResponse {

    String firstOption;
    String secondOption;

}
