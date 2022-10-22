package com.pedrobacchini.imdbcardgame.application.service;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.port.input.MatchOverUseCase;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import com.pedrobacchini.imdbcardgame.application.util.MatchServiceUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MatchOverService implements MatchOverUseCase {

    private final MatchRepositoryPort matchRepositoryPort;

    public MatchOverService(final MatchRepositoryPort matchRepositoryPort) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
    }

    @Override
    public Match execute(final MatchIdentification matchIdentification) {
        return matchRepositoryPort.findByIdentificationAndStatus(matchIdentification, Match.MatchStatus.PLAYING_GAME)
            .map(this::matchOver)
            .orElseThrow(MatchServiceUtils.notFound(matchIdentification, Match.MatchStatus.PLAYING_GAME));
    }

    private Match matchOver(final Match match) {
        match.gameOver();
        matchRepositoryPort.save(match);
        return match;
    }

}
