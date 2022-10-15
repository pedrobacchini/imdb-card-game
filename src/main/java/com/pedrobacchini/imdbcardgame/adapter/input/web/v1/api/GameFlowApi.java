package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.StartAndContinueGameRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Game Flow")
@RequestMapping(value = "/v1/game", produces = APPLICATION_JSON_VALUE)
public interface GameFlowApi {

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    void startAndContinueGame(@Valid @RequestBody StartAndContinueGameRequest startAndContinueGameRequest);

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    void endGame();
}
