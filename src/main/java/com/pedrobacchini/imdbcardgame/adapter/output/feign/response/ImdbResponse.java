package com.pedrobacchini.imdbcardgame.adapter.output.feign.response;

import lombok.Value;

import java.util.List;

@Value
public class ImdbResponse {

    List<ImdbMovie> results;
}
