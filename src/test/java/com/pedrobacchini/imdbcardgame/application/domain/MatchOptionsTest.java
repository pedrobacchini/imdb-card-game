package com.pedrobacchini.imdbcardgame.application.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchOptionsTest {

    @Test
    void givenAValidMatchOptions_whenCallRightOption_thenFirstOptionAsRightOption() {
        final var firstOption = new MatchOption("option1", 10F);
        final var secondOption = new MatchOption("option2", 1F);
        final var actualMatchOptions = new MatchOptions(firstOption, secondOption);
        final var rightOption = actualMatchOptions.isRightOption(firstOption.value());
        assertTrue(rightOption);
    }

    @Test
    void givenAValidMatchOptions_whenCallRightOption_thenSecondOptionAsRightOption() {
        final var firstOption = new MatchOption("option1", 1F);
        final var secondOption = new MatchOption("option2", 10F);
        final var actualMatchOptions = new MatchOptions(firstOption, secondOption);
        final var rightOption = actualMatchOptions.isRightOption(secondOption.value());
        assertTrue(rightOption);
    }

    @Test
    void givenAInvalidMatchOptions_whenCallRightOption_thenShouldReceiveError() {
        final var firstOption = new MatchOption("option1", 1F);
        final var secondOption = new MatchOption("option2", 10F);
        final var actualMatchOptions = new MatchOptions(firstOption, secondOption);
        assertThrows(IllegalArgumentException.class, () -> actualMatchOptions.isRightOption("invaid option"));
    }

}