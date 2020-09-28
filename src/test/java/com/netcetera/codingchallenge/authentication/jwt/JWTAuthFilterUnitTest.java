package com.netcetera.codingchallenge.authentication.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class JWTAuthFilterUnitTest {
    @Mock
    JWTTokenProvider jwtTokenProvider;

    JWTAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(this.jwtTokenProvider.createToken(Mockito.any(), Mockito.any())).thenReturn("token");
        Mockito.when(this.jwtTokenProvider.validateToken("token")).thenReturn(true);
        Mockito.when(this.jwtTokenProvider.getAuthentication("token")).thenReturn(Mockito.mock(Authentication.class));
        Mockito.when(this.jwtTokenProvider.getUsername("token")).thenReturn("testUser");
        Mockito.when(this.jwtTokenProvider.resolveToken(Mockito.any())).thenReturn("token");

        this.jwtAuthFilter = new JWTAuthFilter(this.jwtTokenProvider);
    }

    @Test
    void doFilter() throws IOException, ServletException {
        final FilterChain filterChain = Mockito.spy(FilterChain.class);
        final HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        final HttpServletResponse servletResponse = Mockito.mock(HttpServletResponse.class);

        this.jwtAuthFilter.doFilter(servletRequest, servletResponse, filterChain);

        Mockito.verify(filterChain, Mockito.times(1)).doFilter(servletRequest, servletResponse);
    }
}