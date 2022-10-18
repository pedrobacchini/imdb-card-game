package com.pedrobacchini.imdbcardgame.application.domain;

import java.util.Optional;

public interface MatchOptionsGenerationStrategy {

    MatchOptions generateInitialMatchOptions();
    Optional<MatchOptions> generateNextMatchOptions();

}
