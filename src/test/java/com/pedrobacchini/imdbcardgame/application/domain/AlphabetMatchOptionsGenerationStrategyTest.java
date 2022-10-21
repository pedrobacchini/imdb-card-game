package com.pedrobacchini.imdbcardgame.application.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlphabetMatchOptionsGenerationStrategyTest {

    @Test
    void shoudlCreateAlphabetMatchOptionsGenerationStrategyCorrectly() {
        final var alphabetMatchOptionsGenerationStrategy = new AlphabetMatchOptionsGenerationStrategy();
        assertNotNull(alphabetMatchOptionsGenerationStrategy);
        final var actualInitialMatchOptions = alphabetMatchOptionsGenerationStrategy.generateInitialMatchOptions();
        final var actualInitiaalFirstOption = actualInitialMatchOptions.firstOption();
        assertNotNull(actualInitiaalFirstOption);
        assertNotNull(actualInitiaalFirstOption.option());
        assertNotNull(actualInitiaalFirstOption.score());
        final var actualInitialSecondOption = actualInitialMatchOptions.secondOption();
        assertNotNull(actualInitialSecondOption.option());
        assertNotNull(actualInitialSecondOption.score());
        final var actualNextMatchOptionsOptional = alphabetMatchOptionsGenerationStrategy.generateNextMatchOptions();
        assertTrue(actualNextMatchOptionsOptional.isPresent());
        final var actualNextMatchOptions = actualNextMatchOptionsOptional.get();
        final var actualNextFirstOption = actualNextMatchOptions.firstOption();
        assertNotNull(actualNextFirstOption);
        assertNotNull(actualNextFirstOption.option());
        assertNotNull(actualNextFirstOption.score());
        final var actualNextSecondOption = actualNextMatchOptions.secondOption();
        assertNotNull(actualNextSecondOption.option());
        assertNotNull(actualNextSecondOption.score());
    }

}