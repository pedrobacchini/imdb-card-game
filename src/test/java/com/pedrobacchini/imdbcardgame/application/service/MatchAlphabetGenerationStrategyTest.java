package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MatchAlphabetGenerationStrategyTest {

    @Test
    void AlphabetGenerationStrategy() {
        final var alphabetGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        var matchOptionsOptional = alphabetGenerationStrategy.generateNextMatchOptions();
        while (matchOptionsOptional.isPresent()) {
            final MatchOptions matchOptions = matchOptionsOptional.get();
            System.out.println(matchOptions);
            assertNotNull(matchOptions);
            matchOptionsOptional = alphabetGenerationStrategy.generateNextMatchOptions();
        }
    }



}