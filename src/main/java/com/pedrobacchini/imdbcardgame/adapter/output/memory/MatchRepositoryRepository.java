package com.pedrobacchini.imdbcardgame.adapter.output.memory;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MatchRepositoryRepository implements MatchRepositoryPort {

    public Map<MatchIdentification, Match> gameDatasource = new HashMap<>();

    @Override
    public Optional<Match> findGameByIdentification(final MatchIdentification matchIdentification) {
        return Optional.ofNullable(gameDatasource.get(matchIdentification));
    }

    @Override
    public void save(final Match match) {
        gameDatasource.put(match.getMatchIdentification(), match);
    }

}
