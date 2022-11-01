package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GetRankingService implements GetRankingUseCase {

    public static final int LIMIT_RANKING = 10;
    private final MatchRepositoryPort matchRepositoryPort;

    public GetRankingService(final MatchRepositoryPort matchRepositoryPort) {
        this.matchRepositoryPort = Objects.requireNonNull(matchRepositoryPort);
    }

    @Override
    public List<Match> execute() {
        return matchRepositoryPort.findByStatusOrderedLimitedTo(Match.MatchStatus.GAME_OVER, LIMIT_RANKING);
    }

}
