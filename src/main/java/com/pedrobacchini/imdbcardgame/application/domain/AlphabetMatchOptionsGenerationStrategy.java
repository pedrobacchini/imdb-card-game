package com.pedrobacchini.imdbcardgame.application.domain;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class AlphabetMatchOptionsGenerationStrategy implements MatchOptionsGenerationStrategy {

    private final static int NUMBER_OF_CHOICES = 2;
    private final static ImmutableSet<Character> TYPES_TO_CHOOSE = ImmutableSet.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    private final Set<MatchOptions> matchOptionsAlreadyGenerate = new HashSet<>();

    public MatchOptions generateInitialMatchOptions() {
        Set<MatchOptions> matchOptionsCombinations = generateMatchOptionsCombinations();
        final MatchOptions matchOptions = getRandomMatchOptions(matchOptionsCombinations);
        matchOptionsAlreadyGenerate.add(matchOptions);
        return matchOptions;
    }

    public Optional<MatchOptions> generateNextMatchOptions() {
        Set<MatchOptions> matchOptionsCombinations = generateMatchOptionsCombinations();
        matchOptionsCombinations.removeAll(matchOptionsAlreadyGenerate);
        if (matchOptionsCombinations.isEmpty()) return Optional.empty();
        final MatchOptions matchOptions = getRandomMatchOptions(matchOptionsCombinations);
        matchOptionsAlreadyGenerate.add(matchOptions);
        return Optional.of(matchOptions);
    }

    private MatchOptions getRandomMatchOptions(final Set<MatchOptions> matchOptionsCombinations) {
        return matchOptionsCombinations.stream()
            .toList()
            .get(ThreadLocalRandom.current().nextInt(0, matchOptionsCombinations.size()));
    }

    private Set<MatchOptions> generateMatchOptionsCombinations() {
        return Sets.combinations(TYPES_TO_CHOOSE, NUMBER_OF_CHOICES).stream()
            .map(this::toMatchOptions)
            .collect(Collectors.toSet());
    }

    private MatchOptions toMatchOptions(final Set<Character> characters) {
        final var combination = characters.stream().toList();
        final var randomOption = RandomUtils.nextBoolean();
        final var fisrtOption = combination.get(randomOption ? 0 : 1 );
        final var secondOption = combination.get(randomOption ? 1 : 0);
        return new MatchOptions(
            new MatchOption(fisrtOption.toString(), (float) fisrtOption),
            new MatchOption(secondOption.toString(), (float) secondOption));
    }

}
