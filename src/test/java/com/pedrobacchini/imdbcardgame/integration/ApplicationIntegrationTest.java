package com.pedrobacchini.imdbcardgame.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedrobacchini.imdbcardgame.application.port.output.MatchRepositoryPort;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Tag("integration")
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class ApplicationIntegrationTest {

    @Value("http://localhost:${local.server.port}")
    private String baseUri;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchRepositoryPort matchRepositoryPort;

    @BeforeEach
    public void before() {
        RestAssured.baseURI = baseUri;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config()
            .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper));
        matchRepositoryPort.deleteAll();
    }

}
