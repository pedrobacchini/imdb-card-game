package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constant.ApiRequestMappingConstants;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constant.OpenApiConstants;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.MatchIdentificationRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.request.PlayerMovimentRequest;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.exception.ApiError;
import com.pedrobacchini.imdbcardgame.adapter.security.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Match Flow")
@SecurityRequirement(name = "basicAuth")
@RequestMapping(value = ApiRequestMappingConstants.MATCH, produces = APPLICATION_JSON_VALUE)
public interface MatchFlowApi {

    @Operation(
        summary = "Inicializa uma nova partida ou recupera o status de uma partida já iniciada",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Identificador da partida",
            content = @Content(schema = @Schema(implementation = MatchIdentificationRequest.class)),
            required = true)
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = OpenApiConstants.OK,
            content = @Content(schema = @Schema(implementation = MatchStatusResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = OpenApiConstants.BAD_REQUEST,
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "401",
            description = OpenApiConstants.UNAUTHORIZED,
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    MatchStatusResponse startOrContinueMatch(
        @Valid @RequestBody MatchIdentificationRequest matchIdentificationRequest,
        @AuthenticationPrincipal Player player);

    @Operation(
        summary = "Aplica o movimento do jogador e faz a partida seguir para a proxima fase",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Movimento do jogador",
            content = @Content(schema = @Schema(implementation = PlayerMovimentRequest.class)),
            required = true)
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = OpenApiConstants.OK,
            content = @Content(schema = @Schema(implementation = MatchStatusResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = OpenApiConstants.BAD_REQUEST,
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "401",
            description = OpenApiConstants.UNAUTHORIZED,
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "404",
            description = OpenApiConstants.NOT_FOUND,
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(path = "/move", consumes = APPLICATION_JSON_VALUE)
    MatchStatusResponse nextPhase(
        @Valid @RequestBody PlayerMovimentRequest playerMovimentRequest,
        @AuthenticationPrincipal Player player);

    @Operation(
        summary = "Encerra um partida em andamento",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Identificador da partida",
            content = @Content(schema = @Schema(implementation = MatchIdentificationRequest.class)),
            required = true)
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = OpenApiConstants.OK,
            content = @Content(schema = @Schema(implementation = MatchStatusResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = OpenApiConstants.BAD_REQUEST,
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "401",
            description = OpenApiConstants.UNAUTHORIZED,
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "404",
            description = OpenApiConstants.NOT_FOUND,
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    MatchStatusResponse matchOver(
        @Valid @RequestBody MatchIdentificationRequest matchIdentificationRequest,
        @AuthenticationPrincipal Player player);

}
