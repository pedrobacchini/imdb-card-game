package com.pedrobacchini.imdbcardgame.application.config;

import com.pedrobacchini.imdbcardgame.application.domain.MatchStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("imdb.card.game")
public class ImdbCardGameProperty {

    @Setter
    @Getter
    private MatchStrategy matchStrategy = MatchStrategy.ALPHABET;

    @Getter
    private final ImdbApi imdbApi = new ImdbApi();

    @Getter
    @Setter
    public static class ImdbApi {
        private String key;
        private String language;
    }

}
