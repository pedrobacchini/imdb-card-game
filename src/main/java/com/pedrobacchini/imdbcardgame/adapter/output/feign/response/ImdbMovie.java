package com.pedrobacchini.imdbcardgame.adapter.output.feign.response;

import lombok.Value;

@Value
public class ImdbMovie {

    String title;
    Float voteAverage;
}
