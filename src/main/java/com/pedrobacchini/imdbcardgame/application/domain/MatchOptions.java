package com.pedrobacchini.imdbcardgame.application.domain;

public record MatchOptions(MatchOption firstOption, MatchOption secondOption) {
    public MatchOption rightOption() {
        if (firstOption.score() > secondOption.score()) {
            return firstOption;
        } else {
            return secondOption;
        }
    }

}
