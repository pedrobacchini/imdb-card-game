package com.pedrobacchini.imdbcardgame.application.domain;

import com.google.common.base.Objects;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
public class Match {

    private static final int FAILS_TO_OVER = 3;
    private final MatchIdentification matchIdentification;
    private final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy;
    private int points = 0;
    private int fails = 0;
    private MatchOptions currentMatchOptions;
    private MatchStatus status = MatchStatus.PLAYING_GAME;

    private final LocalDateTime createAt = LocalDateTime.now();

    public enum MatchStatus {
        PLAYING_GAME,
        GAME_OVER
    }

    private Match(
        final MatchIdentification matchIdentification,
        final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy) {
        this.matchIdentification = matchIdentification;
        this.matchOptionsGenerationStrategy = matchOptionsGenerationStrategy;
        if (matchOptionsGenerationStrategy != null) this.currentMatchOptions = matchOptionsGenerationStrategy.generateInitialMatchOptions();
    }

    public void validate() {
        new MatchValidator(this).validate();
    }

    public static Match start(
        final MatchIdentification matchIdentification,
        final MatchOptionsGenerationStrategy matchOptionsGenerationStrategy) {
        return new Match(matchIdentification, matchOptionsGenerationStrategy);
    }

    public void nextPhase(final String playerMove) {
        applyPlayerMovement(playerMove);
        final var matchStatusAfterMovement = analysisMatchAlreadyOverByFails(this.fails);
        switch (matchStatusAfterMovement) {
            case GAME_OVER -> gameOver();
            case PLAYING_GAME -> ifExistsNextOptionUpdateCurrentMatchOptionsElseGameOver();
        }
    }

    public void gameOver() {
        this.status = MatchStatus.GAME_OVER;
        this.currentMatchOptions = null;
    }

    private void applyPlayerMovement(final String playerMove) {
        if (currentMatchOptions.isRightOption(playerMove)) points++;
        else fails++;
    }

    private void ifExistsNextOptionUpdateCurrentMatchOptionsElseGameOver() {
        final var matchOptionsOptional = matchOptionsGenerationStrategy.generateNextMatchOptions();
        if (matchOptionsOptional.isPresent())
            this.currentMatchOptions = matchOptionsOptional.get();
        else gameOver();
    }

    private static MatchStatus analysisMatchAlreadyOverByFails(int fails) {
        if (fails >= FAILS_TO_OVER) return MatchStatus.GAME_OVER;
        else return MatchStatus.PLAYING_GAME;
    }

    public static Comparator<Match> rankingComparator() {
        return Comparator.comparing(Match::getPoints).thenComparing(Match::getCreateAt);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Match match = (Match) o;
        return points == match.points && fails == match.fails && Objects.equal(matchIdentification,
            match.matchIdentification) && Objects.equal(matchOptionsGenerationStrategy,
            match.matchOptionsGenerationStrategy) && Objects.equal(currentMatchOptions,
            match.currentMatchOptions) && status == match.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matchIdentification, matchOptionsGenerationStrategy, points, fails, currentMatchOptions, status);
    }

}
