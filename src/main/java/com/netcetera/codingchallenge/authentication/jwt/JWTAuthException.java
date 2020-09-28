package com.netcetera.codingchallenge.authentication.jwt;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthException extends AuthenticationException {
    private static final long serialVersionUID = 5771013106277531570L;

    public JWTAuthException(final String message) {
        super(message);
    }
}
