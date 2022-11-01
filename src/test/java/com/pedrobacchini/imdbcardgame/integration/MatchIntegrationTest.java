package com.pedrobacchini.imdbcardgame.integration;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.security.Player;
import com.pedrobacchini.imdbcardgame.application.domain.Match;
import com.pedrobacchini.imdbcardgame.application.domain.MatchIdentification;
import com.pedrobacchini.imdbcardgame.application.domain.MatchOption;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchIntegrationTest extends ApplicationIntegrationTest {

    public static final String PLAYER_1_USERNAME = "player1";
    public static final String PLAYER_1_PASS = "player1Pass";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MatchRepositoryPort matchRepositoryPort;

    @ParameterizedTest
    @MethodSource("generateAInvalidMatchIdentificationRequest")
    void givenAInvalidMatchIdentificationRequest_whenCallsStartOrContinueMatchEndpoint_shouldReturnValidationErro(MatchIdentificationRequest request) {
        given()
            .auth().preemptive().basic(PLAYER_1_USERNAME, PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(request)
            .put("/v1/match")
            .then()
            .statusCode(400);
    }

    @Test
    void givenAValidMatchIdentificationRequest_whenCallsStartOrContinueMatchEndpoint_shouldReturnNewMatch() {
        final var extectedMatchId = UUID.randomUUID();
        final var expectedPoints = 0;
        final var expectedFails = 0;
        final var expectedMatchStatus = Match.MatchStatus.PLAYING_GAME;
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());

        final var player1 = (Player) userDetailsService.loadUserByUsername(PLAYER_1_USERNAME);

        final var actualMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        assertEquals(player1.getPlayerId().toString(), actualMatchStatusResponse.getPlayerId());
        assertEquals(matchIdentificationRequest.getMatchId(), actualMatchStatusResponse.getMatchId());
        assertEquals(expectedMatchStatus.toString(), actualMatchStatusResponse.getStatus());
        assertEquals(expectedPoints, actualMatchStatusResponse.getPoints());
        assertEquals(expectedFails, actualMatchStatusResponse.getFails());
        assertNotNull(actualMatchStatusResponse.getFirstOption());
        assertNotNull(actualMatchStatusResponse.getSecondOption());
        assertNotEquals(actualMatchStatusResponse.getFirstOption(), actualMatchStatusResponse.getSecondOption());

        final Match matchInRepository = getMatchInRepository(extectedMatchId, player1);
        assertEquals(player1.getPlayerId(), matchInRepository.getMatchIdentification().playerId());
        assertEquals(extectedMatchId, matchInRepository.getMatchIdentification().matchId());
        assertNotNull(matchInRepository.getMatchOptionsGenerationStrategy());
        assertEquals(expectedPoints, matchInRepository.getPoints());
        assertEquals(expectedFails, matchInRepository.getFails());
        assertEquals(expectedMatchStatus, matchInRepository.getStatus());
        assertNotNull(matchInRepository.getCurrentMatchOptions());
    }

    @Test
    void givenAValidMatchIdentificationRequest_whenCallsStartOrContinueMatchEndpoint_shouldReturnContinueMatch() {
        final var extectedMatchId = UUID.randomUUID();
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());

        final var actualStartMatchStatusResponse = given()
            .auth().preemptive().basic(PLAYER_1_USERNAME, PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        final var actualContinueMatchStatusResponse = given()
            .auth().preemptive().basic(PLAYER_1_USERNAME, PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        assertEquals(actualStartMatchStatusResponse.getPlayerId(), actualContinueMatchStatusResponse.getPlayerId());
        assertEquals(actualStartMatchStatusResponse.getMatchId(), actualContinueMatchStatusResponse.getMatchId());
        assertEquals(actualStartMatchStatusResponse.getStatus(), actualContinueMatchStatusResponse.getStatus());
        assertEquals(actualStartMatchStatusResponse.getPoints(), actualContinueMatchStatusResponse.getPoints());
        assertEquals(actualStartMatchStatusResponse.getFails(), actualContinueMatchStatusResponse.getFails());
        assertEquals(actualStartMatchStatusResponse.getFirstOption(), actualContinueMatchStatusResponse.getFirstOption());
        assertEquals(actualStartMatchStatusResponse.getSecondOption(), actualContinueMatchStatusResponse.getSecondOption());
    }

    @ParameterizedTest
    @MethodSource("generateAInvalidPlayerMovementCommand")
    void givenAInvalidPlayerMovementCommand_whenCallsNextMatchPhaseEndpoint_shouldReturnMatchWithNewStatus(PlayerMovimentRequest playerMovimentRequest) {
        given()
            .auth().preemptive().basic(PLAYER_1_USERNAME, PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(playerMovimentRequest)
            .post("/v1/match/move")
            .then()
            .statusCode(400);
    }

    @Test
    void givenAValidRightPlayerMovementCommand_whenCallsNextMatchPhaseEndpoint_shouldReturnMatchWithNewStatus() {
        final var player1 = (Player) userDetailsService.loadUserByUsername(PLAYER_1_USERNAME);
        final var extectedMatchId = UUID.randomUUID();
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());

        final var actualStartMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        final Match matchInRepository = getMatchInRepository(extectedMatchId, player1);

        final var playerMovimentRequest = new PlayerMovimentRequest(
            matchIdentificationRequest.getMatchId(),
            matchInRepository.getCurrentMatchOptions().rightOption().value());

        final var actualNextPhaseMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(playerMovimentRequest)
            .post("/v1/match/move")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        assertEquals(actualStartMatchStatusResponse.getPlayerId(), actualNextPhaseMatchStatusResponse.getPlayerId());
        assertEquals(actualStartMatchStatusResponse.getMatchId(), actualNextPhaseMatchStatusResponse.getMatchId());
        assertEquals(actualStartMatchStatusResponse.getStatus(), actualNextPhaseMatchStatusResponse.getStatus());
        assertEquals(actualStartMatchStatusResponse.getPoints() + 1, actualNextPhaseMatchStatusResponse.getPoints());
        assertEquals(actualStartMatchStatusResponse.getFails(), actualNextPhaseMatchStatusResponse.getFails());
        assertNotNull(actualNextPhaseMatchStatusResponse.getFirstOption());
        assertNotNull(actualNextPhaseMatchStatusResponse.getSecondOption());
    }

    @Test
    void givenAValidWrongPlayerMovementCommand_whenCallsNextMatchPhaseEndpoint_shouldReturnMatchWithNewStatus() {
        final var extectedMatchId = UUID.randomUUID();
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());
        final var player1 = (Player) userDetailsService.loadUserByUsername(PLAYER_1_USERNAME);

        final var actualStartMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        final Match matchInRepository = getMatchInRepository(extectedMatchId, player1);

        final var playerMovimentRequest = new PlayerMovimentRequest(
            matchIdentificationRequest.getMatchId(),
            matchInRepository.getCurrentMatchOptions().wrongOption().value());

        final var actualNextPhaseMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(playerMovimentRequest)
            .post("/v1/match/move")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        assertEquals(actualStartMatchStatusResponse.getPlayerId(), actualNextPhaseMatchStatusResponse.getPlayerId());
        assertEquals(actualStartMatchStatusResponse.getMatchId(), actualNextPhaseMatchStatusResponse.getMatchId());
        assertEquals(actualStartMatchStatusResponse.getStatus(), actualNextPhaseMatchStatusResponse.getStatus());
        assertEquals(actualStartMatchStatusResponse.getPoints(), actualNextPhaseMatchStatusResponse.getPoints());
        assertEquals(actualStartMatchStatusResponse.getFails() + 1, actualNextPhaseMatchStatusResponse.getFails());
        assertNotNull(actualNextPhaseMatchStatusResponse.getFirstOption());
        assertNotNull(actualNextPhaseMatchStatusResponse.getSecondOption());
    }

    @Test
    void givenManyValidPlayerMovementCommand_whenCallsNextMatchPhaseEndpoint_shouldReturnOverMatch() {
        final var extectedMatchId = UUID.randomUUID();
        final var expectedMatchStatus = Match.MatchStatus.GAME_OVER;
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());
        final var player1 = (Player) userDetailsService.loadUserByUsername(PLAYER_1_USERNAME);

        final var actualStartMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        var lastMatchStatusResponse = actualStartMatchStatusResponse;
        var expectedPoints = 0;
        var expectedFails = 0;
        while (lastMatchStatusResponse.getStatus().equals(Match.MatchStatus.PLAYING_GAME.toString())) {
            final Match matchInRepository = getMatchInRepository(extectedMatchId, player1);

            final var randomOption = RandomUtils.nextBoolean();

            MatchOption option;
            if(randomOption) {
                option = matchInRepository.getCurrentMatchOptions().rightOption();
                expectedPoints++;
            }
            else {
                option = matchInRepository.getCurrentMatchOptions().wrongOption();
                expectedFails++;
            }

            final var playerMovimentRequest = new PlayerMovimentRequest(
                matchIdentificationRequest.getMatchId(), option.value());

            lastMatchStatusResponse = given()
                .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
                .contentType(ContentType.JSON)
                .when()
                .body(playerMovimentRequest)
                .post("/v1/match/move")
                .then()
                .statusCode(200)
                .extract()
                .as(MatchStatusResponse.class);
        }

        assertEquals(actualStartMatchStatusResponse.getPlayerId(), lastMatchStatusResponse.getPlayerId());
        assertEquals(actualStartMatchStatusResponse.getMatchId(), lastMatchStatusResponse.getMatchId());
        assertEquals(expectedMatchStatus.toString(), lastMatchStatusResponse.getStatus());
        assertEquals(expectedPoints, lastMatchStatusResponse.getPoints());
        assertEquals(expectedFails, lastMatchStatusResponse.getFails());
        assertNull(lastMatchStatusResponse.getFirstOption());
        assertNull(lastMatchStatusResponse.getSecondOption());
    }

    @Test
    void givenManyValidWrongPlayerMovementCommand_whenCallsNextMatchPhaseEndpoint_shouldReturnOverMatch() {
        final var extectedMatchId = UUID.randomUUID();
        final var expectedPoints = 0;
        final var expectedFails = 3;
        final var expectedMatchStatus = Match.MatchStatus.GAME_OVER;
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());
        final var player1 = (Player) userDetailsService.loadUserByUsername(PLAYER_1_USERNAME);

        final var actualStartMatchStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        var lastMatchStatusResponse = actualStartMatchStatusResponse;
        while (lastMatchStatusResponse.getStatus().equals(Match.MatchStatus.PLAYING_GAME.toString())) {
            final Match matchInRepository = getMatchInRepository(extectedMatchId, player1);

            final var playerMovimentRequest = new PlayerMovimentRequest(
                matchIdentificationRequest.getMatchId(),
                matchInRepository.getCurrentMatchOptions().wrongOption().value());

            lastMatchStatusResponse = given()
                .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
                .contentType(ContentType.JSON)
                .when()
                .body(playerMovimentRequest)
                .post("/v1/match/move")
                .then()
                .statusCode(200)
                .extract()
                .as(MatchStatusResponse.class);
        }

        assertEquals(actualStartMatchStatusResponse.getPlayerId(), lastMatchStatusResponse.getPlayerId());
        assertEquals(actualStartMatchStatusResponse.getMatchId(), lastMatchStatusResponse.getMatchId());
        assertEquals(expectedMatchStatus.toString(), lastMatchStatusResponse.getStatus());
        assertEquals(expectedPoints, lastMatchStatusResponse.getPoints());
        assertEquals(expectedFails, lastMatchStatusResponse.getFails());
        assertNull(lastMatchStatusResponse.getFirstOption());
        assertNull(lastMatchStatusResponse.getSecondOption());
    }

    @ParameterizedTest
    @MethodSource("generateAInvalidMatchIdentificationRequest")
    void givenAInvalidMatchIdentificationRequest_whenCallsMatchOverEndpoint_shouldReturnValidationErro(MatchIdentificationRequest request) {
        given()
            .auth().preemptive().basic(PLAYER_1_USERNAME, PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(request)
            .delete("/v1/match")
            .then()
            .statusCode(400);
    }

    @Test
    void givenAValidMatchIdentificationRequest_whenCallsMatchOverEndpoint_shouldReturnMatchOver() {
        final var extectedMatchId = UUID.randomUUID();
        final var expectedPoints = 0;
        final var expectedFails = 0;
        final var expectedMatchStatus = Match.MatchStatus.GAME_OVER;
        final var matchIdentificationRequest = new MatchIdentificationRequest(extectedMatchId.toString());

        final var player1 = (Player) userDetailsService.loadUserByUsername(PLAYER_1_USERNAME);

        given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .put("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        final var actualMatchOverStatusResponse = given()
            .auth().preemptive().basic(player1.getUsername(), PLAYER_1_PASS)
            .contentType(ContentType.JSON)
            .when()
            .body(matchIdentificationRequest)
            .delete("/v1/match")
            .then()
            .statusCode(200)
            .extract()
            .as(MatchStatusResponse.class);

        assertEquals(player1.getPlayerId().toString(), actualMatchOverStatusResponse.getPlayerId());
        assertEquals(matchIdentificationRequest.getMatchId(), actualMatchOverStatusResponse.getMatchId());
        assertEquals(expectedMatchStatus.toString(), actualMatchOverStatusResponse.getStatus());
        assertEquals(expectedPoints, actualMatchOverStatusResponse.getPoints());
        assertEquals(expectedFails, actualMatchOverStatusResponse.getFails());
        assertNull(actualMatchOverStatusResponse.getFirstOption());
        assertNull(actualMatchOverStatusResponse.getSecondOption());

        final Match matchInRepository = getMatchInRepository(extectedMatchId, player1);
        assertEquals(player1.getPlayerId(), matchInRepository.getMatchIdentification().playerId());
        assertEquals(extectedMatchId, matchInRepository.getMatchIdentification().matchId());
        assertNotNull(matchInRepository.getMatchOptionsGenerationStrategy());
        assertEquals(expectedPoints, matchInRepository.getPoints());
        assertEquals(expectedFails, matchInRepository.getFails());
        assertEquals(expectedMatchStatus, matchInRepository.getStatus());
        assertNull(matchInRepository.getCurrentMatchOptions());
    }

    private static Stream<Arguments> generateAInvalidMatchIdentificationRequest() {
        final var requestNUll = new MatchIdentificationRequest(null);
        final var requestEmpty = new MatchIdentificationRequest(null);
        final var requestInvalidUuid = new MatchIdentificationRequest("123");
        return Stream.of(
            Arguments.arguments(requestNUll),
            Arguments.arguments(requestEmpty),
            Arguments.arguments(requestInvalidUuid));
    }

    private static Stream<Arguments> generateAInvalidPlayerMovementCommand() {
        final var requestMatchIdNUll = new PlayerMovimentRequest(null, "A");
        final var requestMatchIdEmpty = new PlayerMovimentRequest("", "A");
        final var requestMatchIdInvalidUuid = new PlayerMovimentRequest("123", "A");
        final var requestPlayerMoveNull = new PlayerMovimentRequest(UUID.randomUUID().toString(), null);
        final var requestPlayerMoveEmpty = new PlayerMovimentRequest(UUID.randomUUID().toString(), "");
        return Stream.of(
            Arguments.arguments(requestMatchIdNUll),
            Arguments.arguments(requestMatchIdEmpty),
            Arguments.arguments(requestMatchIdInvalidUuid),
            Arguments.arguments(requestPlayerMoveNull),
            Arguments.arguments(requestPlayerMoveEmpty));
    }

    private Match getMatchInRepository(final UUID extectedMatchId, final Player player1) {
        final var matchInRepositoryOptional = matchRepositoryPort.findByIdentification(
            new MatchIdentification(player1.getPlayerId(), extectedMatchId));
        assertTrue(matchInRepositoryOptional.isPresent());
        return matchInRepositoryOptional.get();
    }

}