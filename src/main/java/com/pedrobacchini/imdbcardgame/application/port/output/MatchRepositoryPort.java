package com.pedrobacchini.imdbcardgame.application.port.output;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;

import java.util.Optional;

public interface MatchRepositoryPort {

    Optional<Match> findGameByIdentification(MatchIdentification matchIdentification);

    void save(Match match);
}
