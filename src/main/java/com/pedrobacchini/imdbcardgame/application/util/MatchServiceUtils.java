package com.pedrobacchini.imdbcardgame.application.util;

import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.exception.NotFoundException;

import java.util.function.Supplier;

public class MatchServiceUtils {

    private MatchServiceUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Supplier<NotFoundException> notFound(final MatchIdentification matchIdentification, final Match.MatchStatus matchStatus) {
        return () -> new NotFoundException("Match with Identification %s and status %s was not found".formatted(matchIdentification, matchStatus));
    }
}
