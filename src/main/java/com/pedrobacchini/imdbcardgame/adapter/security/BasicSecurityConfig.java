package com.pedrobacchini.imdbcardgame.adapter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

@Configuration
public class BasicSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BasicSecurityConfig(
        final UserDetailsService userDetailsService,
        final RestAuthenticationEntryPoint authenticationEntryPoint,
        final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = Objects.requireNonNull(userDetailsService);
        this.authenticationEntryPoint = Objects.requireNonNull(authenticationEntryPoint);
        this.bCryptPasswordEncoder = Objects.requireNonNull(bCryptPasswordEncoder);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(SecurityConstants.TEST_PUBLIC_MATCHERS).permitAll()
            .antMatchers(SecurityConstants.PUBLIC_MATCHERS).permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable();
        return http.build();
    }

}
