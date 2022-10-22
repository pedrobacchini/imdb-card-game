package com.pedrobacchini.imdbcardgame.adapter.security;

public class SecurityConstants {

    public static final String[] TEST_PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public static final String[] PUBLIC_MATCHERS = {
            "/v1/ranking",
    };
}
