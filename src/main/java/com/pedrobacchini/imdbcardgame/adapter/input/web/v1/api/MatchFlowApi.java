package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Match Flow")
@RequestMapping(value = "/v1/match", produces = APPLICATION_JSON_VALUE)
public interface MatchFlowApi {

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    MatchStatusResponse startOrContinueMatch(@Valid @RequestBody MatchIdentificationRequest matchIdentificationRequest);

    @PostMapping(path = "/move",consumes = APPLICATION_JSON_VALUE)
    MatchStatusResponse nextPhase(@Valid @RequestBody PlayerMovimentRequest playerMovimentRequest);

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    MatchStatusResponse matchOver(@Valid @RequestBody MatchIdentificationRequest matchIdentificationRequest);
}
