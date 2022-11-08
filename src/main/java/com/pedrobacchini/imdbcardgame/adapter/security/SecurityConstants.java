package com.pedrobacchini.imdbcardgame.adapter.security;

import com.pedrobacchini.imdbcardgame.adapter.input.web.v1.api.constant.ApiRequestMappingConstants;

public class SecurityConstants {

    public static final String[] TEST_PUBLIC_MATCHERS = {
        "/api-docs/**",
        "/swagger-ui/**"
    };

    public static final String[] PUBLIC_MATCHERS = {
        ApiRequestMappingConstants.RANKING,
    };

}
