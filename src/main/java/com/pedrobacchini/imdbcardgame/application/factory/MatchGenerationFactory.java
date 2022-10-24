package com.pedrobacchini.imdbcardgame.application.factory;

import com.pedrobacchini.imdbcardgame.application.config.ImdbCardGameProperty;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOptionsGenerationStrategy;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Service
public class MatchGenerationFactory {

    private final ImdbCardGameProperty imdbCardGameProperty;

    public MatchGenerationFactory(final ImdbCardGameProperty imdbCardGameProperty) {
        this.imdbCardGameProperty = Objects.requireNonNull(imdbCardGameProperty);
    }

    public MatchOptionsGenerationStrategy acquireNewStrategy() {
        try {
            return (MatchOptionsGenerationStrategy) imdbCardGameProperty.getMatchStrategy()
                .getMatchOptionsGenerationStrategy().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
