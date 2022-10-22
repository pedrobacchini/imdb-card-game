package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Ranking")
@RequestMapping(value = "/v1/ranking", produces = APPLICATION_JSON_VALUE)
public interface RankingApi {

    @GetMapping
    List<MatchStatusResponse> ranking();

}
