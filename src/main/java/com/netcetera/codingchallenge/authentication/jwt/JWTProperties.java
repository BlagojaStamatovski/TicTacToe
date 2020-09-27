package com.netcetera.codingchallenge.authentication.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JWTProperties {

    @Value("${jwt.secret.keyAlias}")
    private String keyAlias;

    @Value("${jwt.secret.keyStoreAlias}")
    private String keyStoreAlias;

    @Value("${jwt.secret.keyStorePassword}")
    private String keyStorePassword;

    @Value("${jwt.secret.keyStoreName}")
    private String keyStoreName;

    @Value("${jwt.token.validityInMs}")
    private long validityInMs;
}
