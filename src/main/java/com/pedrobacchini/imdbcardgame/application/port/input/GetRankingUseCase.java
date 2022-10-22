package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.domain.Match;

import java.util.List;

public interface GetRankingUseCase {

    List<Match> execute();

}
