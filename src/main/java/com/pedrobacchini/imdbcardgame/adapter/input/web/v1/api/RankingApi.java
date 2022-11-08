package com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constant.ApiRequestMappingConstants;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constant.OpenApiConstants;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.response.MatchStatusResponse;
import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Ranking")
@RequestMapping(value = ApiRequestMappingConstants.RANKING, produces = APPLICATION_JSON_VALUE)
public interface RankingApi {

    @Operation(summary = "Obtem ranking das partidas ja finalizadas")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = OpenApiConstants.OK,
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MatchStatusResponse.class)))),
        @ApiResponse(
            responseCode = "400",
            description = OpenApiConstants.BAD_REQUEST,
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    List<MatchStatusResponse> ranking();

}
