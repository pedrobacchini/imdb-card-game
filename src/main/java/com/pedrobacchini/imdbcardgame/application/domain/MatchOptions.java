package com.pedrobacchini.imdbcardgame.application.domain;

import java.util.Objects;

public record MatchOptions(MatchOption firstOption, MatchOption secondOption) {

    public boolean isRightOption(final String option) {
        if (option.equals(firstOption().value())) {
            return isRightOption(firstOption());
        } else if (option.equals(secondOption().value())) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MatchOptions that = (MatchOptions) o;
        return (Objects.equals(firstOption.value(), that.firstOption.value()) && Objects.equals(secondOption.value(), that.secondOption.value())) ||
            (Objects.equals(firstOption.value(), that.secondOption.value()) && Objects.equals(secondOption.value(), that.firstOption.value()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstOption.value(), secondOption.value()) + Objects.hash(secondOption.value(), firstOption.value());
    }
}
