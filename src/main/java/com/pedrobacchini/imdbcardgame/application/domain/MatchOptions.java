package com.pedrobacchini.imdbcardgame.application.domain;

public record MatchOptions(MatchOption firstOption, MatchOption secondOption) {

    public boolean isRightOption(final String option) {
        if (option.equals(firstOption().option())) {
            return isRightOption(firstOption());
        } else if (option.equals(secondOption().option())) {
            return isRightOption(secondOption());
        } else {
            throw new IllegalArgumentException("invalid option");
        }
    }

    public boolean isRightOption(final MatchOption matchOption) {
        return matchOption.equals(rightOption());
    }

    public MatchOption rightOption() {
        if (firstOption.score() > secondOption.score()) return firstOption;
        else return secondOption;
    }

    public MatchOption wrongOption() {
        if (firstOption.score() < secondOption.score()) return firstOption;
        else return secondOption;
    }
}
