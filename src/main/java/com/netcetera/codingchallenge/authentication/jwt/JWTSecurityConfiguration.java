package com.netcetera.codingchallenge.authentication.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JWTSecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final JWTAuthFilter jwtAuthFilter = new JWTAuthFilter(this.jwtTokenProvider);
        http.exceptionHandling()
                .authenticationEntryPoint(new JWTAuthEntryPoint())
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}