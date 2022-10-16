package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.ContinueOrStartMatchRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchCurrentStatusResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Match Flow")
@RequestMapping(value = "/v1/match", produces = APPLICATION_JSON_VALUE)
public interface MatchFlowApi {

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    MatchCurrentStatusResponse startAndContinueMatch(@Valid @RequestBody ContinueOrStartMatchRequest continueOrStartMatchRequest);

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    void endMatch();
}