package com.pedrobacchini.imdbcardgame.util;

import com.pedrobacchini.imdbcardgame.application.domain.Match;

public class MatchTestUtil {

    public static Match forceGameOverByFails(Match match) {
        while (match.getStatus().equals(Match.MatchStatus.PLAYING_GAME)) {
            final var matchOption = match.getCurrentMatchOptions().wrongOption();
            match.nextPhase(matchOption.option());
        }
        return match;
    }
}
