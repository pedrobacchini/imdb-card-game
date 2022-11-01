package com.pedrobacchini.imdbcardgame.application.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlphabetMatchOptionsGenerationStrategyTest {

    @Test
    void givenCreatedAlphabetMatchOptionsGenerationStrategy_whenCallGenerateInitialMatchOptions_thenGenerateMatchOptionsCorrectly() {
        final var alphabetMatchOptionsGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        assertNotNull(alphabetMatchOptionsGenerationStrategy);
        final var actualInitialMatchOptions = alphabetMatchOptionsGenerationStrategy.generateInitialMatchOptions();
        assertNotNull(actualInitialMatchOptions);
        assertNotNull(actualInitialMatchOptions.firstOption());
        assertNotNull(actualInitialMatchOptions.firstOption().value());
        assertNotNull(actualInitialMatchOptions.firstOption().score());
        assertNotNull(actualInitialMatchOptions.secondOption());
        assertNotNull(actualInitialMatchOptions.secondOption().value());
        assertNotNull(actualInitialMatchOptions.secondOption().score());
    }

    @Test
    void givenCreatedAlphabetMatchOptionsGenerationStrategy_whenCallGenerateNextMatchOptions_thenGenerateMatchOptionsCorrectly() {
        final var alphabetMatchOptionsGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        assertNotNull(alphabetMatchOptionsGenerationStrategy);
        final var actualNextMatchOptionsOptional = alphabetMatchOptionsGenerationStrategy.generateNextMatchOptions();
        assertTrue(actualNextMatchOptionsOptional.isPresent());
        final var actualNextMatchOptions = actualNextMatchOptionsOptional.get();
        assertNotNull(actualNextMatchOptions.firstOption());
        assertNotNull(actualNextMatchOptions.firstOption().value());
        assertNotNull(actualNextMatchOptions.firstOption().score());
        assertNotNull(actualNextMatchOptions.secondOption());
        assertNotNull(actualNextMatchOptions.secondOption().value());
        assertNotNull(actualNextMatchOptions.secondOption().score());
    }

}