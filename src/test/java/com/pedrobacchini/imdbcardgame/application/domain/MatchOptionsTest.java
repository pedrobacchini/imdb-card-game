package com.pedrobacchini.imdbcardgame.application.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchOptionsTest {

    @Test
    void givenAValidMatchOptions_whenCallRightOption_thenFirstOptionAsRightOption() {
        final var firstOption = new MatchOption("Z", (float) 'Z');
        final var secondOption = new MatchOption("A", (float) 'A');
        final var actualMatchOptions = new MatchOptions(firstOption, secondOption);
        final var actualRightOption = actualMatchOptions.rightOption();
        assertEquals(firstOption, actualRightOption);
    }

    @Test
    void givenAValidMatchOptions_whenCallRightOption_thenSecondOptionAsRightOption() {
        final var firstOption = new MatchOption("A", (float) 'A');
        final var secondOption = new MatchOption("B", (float) 'B');
        final var actualMatchOptions = new MatchOptions(firstOption, secondOption);
        final var actualRightOption = actualMatchOptions.rightOption();
        assertEquals(secondOption, actualRightOption);
    }

}