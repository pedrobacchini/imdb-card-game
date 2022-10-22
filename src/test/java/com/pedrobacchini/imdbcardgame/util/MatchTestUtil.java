package com.pedrobacchini.imdbcardgame.util;

import com.pedrobacchini.imdbcardgame.application.domain.AlphabetMatchOptionsGenerationStrategy;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class MatchTestUtil {

    public static Match forceGameOverByFails(Match match) {
        while (match.getStatus().equals(Match.MatchStatus.PLAYING_GAME)) {
            final var matchOption = match.getCurrentMatchOptions().wrongOption();
            match.nextPhase(matchOption.option());
        }
        return match;
    }

    public static Match generateMatchOver() {
        MatchIdentification matchIdentification = new MatchIdentification(UUID.randomUUID(), UUID.randomUUID());
        final var match = Match.start(matchIdentification, new AlphabetMatchOptionsGenerationStrategy());
        while (match.getStatus().equals(Match.MatchStatus.PLAYING_GAME)) {
            final var randomOption = RandomUtils.nextBoolean();
            final var matchOption = randomOption ? match.getCurrentMatchOptions().rightOption() : match.getCurrentMatchOptions().wrongOption();
            match.nextPhase(matchOption.option());
        }
        return match;
    }

    public static List<Match> generateListMatchOver(int size) {
        final List<Match> matchList = new ArrayList<>();
        while (matchList.size() < size) {
            matchList.add(generateMatchOver());
        }
        return matchList;
    }

    public static Stream<Match> generateStreamMatchOver(int size) {
        return generateListMatchOver(size).stream();
    }

}
