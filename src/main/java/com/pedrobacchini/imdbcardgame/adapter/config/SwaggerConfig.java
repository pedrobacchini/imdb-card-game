package com.pedrobacchini.imdbcardgame.adapter.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "basicAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "basic"
)
public class SwaggerConfig {

    private static final String INFO_CONTACT_NAME = "Pedro Bacchini";
    private static final String INFO_CONTACT_URL = "https://github.com/pedrobacchini";
    private static final String INFO_CONTACT_EMAIL = "pedrobacchini@outlook.com";
    private static final String INFO_TITLE = "IMDB Card Game - Rest API";
    private static final String INFO_DESCRIPTION = "API REST ao estilo card game, onde serão informados dois filmes e o jogador deve acertar aquele que possui melhor avaliação no IMDB.";
    private static final String INFO_VERSION = "v1.0.0";
    private static final String INFO_LICENSE_NAME = "Apache License Version 2.0";
    private static final String INFO_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

    @Bean
    public Info info() {
        return new Info()
            .title(INFO_TITLE)
            .contact(new Contact().name(INFO_CONTACT_NAME).url(INFO_CONTACT_URL).email(INFO_CONTACT_EMAIL))
            .description(INFO_DESCRIPTION)
            .version(INFO_VERSION)
            .license(new License().name(INFO_LICENSE_NAME).url(INFO_LICENSE_URL));
    }

    @Bean
    public Components components() {
        return new Components();
    }

    @Bean
    public OpenAPI customOpenAPI(Info info, Components components) {
        return new OpenAPI()
            .components(components)
            .info(info);
    }

}
