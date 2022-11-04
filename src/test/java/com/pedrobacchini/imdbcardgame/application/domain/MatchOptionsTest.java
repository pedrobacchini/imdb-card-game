package com.pedrobacchini.imdbcardgame.application.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void givenAValidMatchOptionsEquals_whenCallEquals_thenShouldReturnEquals() {
        final var option1 = new MatchOption("option1", 1F);
        final var option2 = new MatchOption("option2", 1F);
        final var actualMatchOptions1 = new MatchOptions(option1, option2);
        final var actualMatchOptions2 = new MatchOptions(option1, option2);
        assertEquals(actualMatchOptions1, actualMatchOptions2);
        final var actualMatchOptions3 = new MatchOptions(option2, option1);
        assertEquals(actualMatchOptions1, actualMatchOptions3);

        final var actualMatchOptions = new HashSet<>(Set.of(actualMatchOptions1));
        actualMatchOptions.remove(new MatchOptions(option2, option1));
        assertEquals(0, actualMatchOptions.size());
    }

}