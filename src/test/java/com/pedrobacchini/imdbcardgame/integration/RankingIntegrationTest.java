package com.pedrobacchini.imdbcardgame.integration;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.converter.MatchConverterHelper;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import com.pedrobacchini.imdbcardgame.util.MatchTestUtil;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingIntegrationTest extends ApplicationIntegrationTest {

    @Autowired
    private MatchRepositoryPort matchRepositoryPort;

    @Test
    void givenAValidMatchOver_whenCallsRankingEndpoint_shouldReturnAMatchOver() {
        final var match = MatchTestUtil.generateMatchOver();
        matchRepositoryPort.save(match);

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/v1/ranking")
            .then()
            .statusCode(200)
            .body("size()", Matchers.equalTo(1));
    }

    @Test
    void givenAValid5MatchOver_whenCallsRankingEndpoint_shouldReturn5MatchOverOrdened() {
        final var expectedMatchSize = 5;
        final var matches = MatchTestUtil.generateListMatchOver(expectedMatchSize);
        matches.forEach(matchRepositoryPort::save);

        final var as = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/v1/ranking")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse[].class);

        final var expectedMatches = matches.stream()
            .sorted(Match.rankingComparator())
            .map(MatchConverterHelper::toMatchStatusResponse)
            .toList();
        assertEquals(expectedMatchSize, as.length);
        assertThat(expectedMatches).hasSameElementsAs(Arrays.stream(as).toList());
    }

    @Test
    void givenAValid15MatchOver_whenCallsRankingEndpoint_shouldReturn10MatchOver() {
        final var expectedMatchSize = 10;
        final var matches = MatchTestUtil.generateListMatchOver(15);
        matches.forEach(matchRepositoryPort::save);

        final var as = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/v1/ranking")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse[].class);

        final var expectedMatches = matches.stream()
            .sorted(Match.rankingComparator())
            .limit(expectedMatchSize)
            .map(MatchConverterHelper::toMatchStatusResponse)
            .toList();
        assertEquals(expectedMatchSize, as.length);
        assertThat(expectedMatches).hasSameElementsAs(Arrays.stream(as).toList());
    }

}
