package com.pedrobacchini.imdbcardgame.application.port.output;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;

import java.util.Collection;
import java.util.Optional;

public interface MatchRepositoryPort {

    Optional<Match> findByIdentification(MatchIdentification matchIdentification);

    Optional<Match> findByIdentificationAndStatus(final MatchIdentification matchIdentification, final Match.MatchStatus matchStatus);

    void save(Match match);

    Collection<Match> findAllByStatus(Match.MatchStatus gameOver);

}
