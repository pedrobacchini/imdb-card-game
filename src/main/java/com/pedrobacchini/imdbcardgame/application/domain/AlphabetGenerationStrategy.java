package com.pedrobacchini.imdbcardgame.application.domain;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.RandomUtils;
import org.paukov.combinatorics3.Generator;

import java.util.Iterator;
import java.util.List;

public class AlphabetGenerationStrategy implements MatchGenerationStrategy {

    private final static int NUMBER_OF_CHOICES = 2;
    private final static ImmutableSet<Character> TYPES_TO_CHOOSE = ImmutableSet.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    private final Iterator<MatchOptions> combinationsIterator;

    public AlphabetGenerationStrategy() {
        this.combinationsIterator = Generator.combination(TYPES_TO_CHOOSE)
            .simple(NUMBER_OF_CHOICES)
            .stream()
            .sorted(this::randomize)
            .map(this::generatingMatchOptions)
            .iterator();
    }

    public boolean hasNext() {
        return combinationsIterator.hasNext();
    }

    public MatchOptions next() {
        return combinationsIterator.next();
    }

    private MatchOptions generatingMatchOptions(final List<Character> combination) {
        final var fisrtOption = combination.get(0);
        final var secondOption = combination.get(1);
        return new MatchOptions(
            new MatchOption(fisrtOption.toString(), (float) fisrtOption),
            new MatchOption(secondOption.toString(), (float) secondOption));
    }

    private int randomize(List<Character> o1, List<Character> o2) {
        return RandomUtils.nextInt(0, 2) - 1;
    }

}
