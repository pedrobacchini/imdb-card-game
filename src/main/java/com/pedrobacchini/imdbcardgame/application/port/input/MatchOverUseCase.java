package com.pedrobacchini.imdbcardgame.application.port.input;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;

public interface MatchOverUseCase {

    Match execute(MatchIdentification matchIdentification);

}
