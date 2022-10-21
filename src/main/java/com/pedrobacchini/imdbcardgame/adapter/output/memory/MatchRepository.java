package com.pedrobacchini.imdbcardgame.adapter.output.memory;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MatchRepository implements MatchRepositoryPort {

    private static final Map<MatchIdentification, Match> gameDatasource = new HashMap<>();

    @Override
    public Optional<Match> findByIdentification(final MatchIdentification matchIdentification) {
        return Optional.ofNullable(gameDatasource.get(matchIdentification));
    }

    @Override
    public Optional<Match> findByIdentificationAndStatus(
        final MatchIdentification matchIdentification,
        final Match.MatchStatus matchStatus) {
        return findByIdentification(matchIdentification)
            .map(match -> {
                if (match.getStatus().equals(matchStatus)) return match;
                else return null;
            });
    }

    @Override
    public void save(final Match match) {
        gameDatasource.put(match.getMatchIdentification(), match);
    }

}
