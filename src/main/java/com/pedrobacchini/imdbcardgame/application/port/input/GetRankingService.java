package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.adapter.output.memory.MatchRepository;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GetRankingService implements GetRankingUseCase {

    public static final int LIMIT_RANKING = 10;
    private final MatchRepository matchRepository;

    public GetRankingService(final MatchRepository matchRepository) {
        this.matchRepository = Objects.requireNonNull(matchRepository);
    }

    @Override
    public List<Match> execute() {
        return matchRepository.findAllByStatus(Match.MatchStatus.GAME_OVER)
            .stream()
            .sorted((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()))
            .limit(LIMIT_RANKING)
            .collect(Collectors.toList());
    }

}
