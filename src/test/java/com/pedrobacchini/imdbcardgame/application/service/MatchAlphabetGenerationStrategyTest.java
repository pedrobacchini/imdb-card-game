package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptions;
import org.junit.jupiter.api.Test;

class MatchAlphabetGenerationStrategyTest {

    @Test
    void AlphabetGenerationStrategy() {
        final var alphabetGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        var matchOptionsOptional = alphabetGenerationStrategy.generateNextMatchOptions();
        while (matchOptionsOptional.isPresent()) {
            final MatchOptions matchOptions = matchOptionsOptional.get();
            System.out.println(matchOptions);
            matchOptionsOptional = alphabetGenerationStrategy.generateNextMatchOptions();
        }
    }



}