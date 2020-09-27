package com.netcetera.codingchallenge.authentication.jwt;

import com.netcetera.codingchallenge.authentication.jwt.api.JWTAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthFilter extends GenericFilterBean {

    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
            throws IOException, ServletException {

        final String token = this.jwtTokenProvider.resolveToken((HttpServletRequest) req);

        boolean validToken = false;
        try {
            validToken = this.jwtTokenProvider.validateToken(token);
        } catch (final JWTAuthException e) {
            //
        }

        if (token != null && validToken) {
            final Authentication auth = this.jwtTokenProvider.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(req, res);
    }
}
